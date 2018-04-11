package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.entity.RecordCard;
import ru.mera.lib.repository.BookRepository;
import ru.mera.lib.entity.Book;
import ru.mera.lib.repository.RecordCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RecordCardRepository recordCardRepository;

    @Autowired
    private PupilService pupilService;

    private boolean bookNotExist(Book book){
        Book bookFromDB = bookRepository.findByTitleIgnoreCaseAndAuthorIgnoreCaseAndPublishYearAndClassNumber(book.getTitle(),
                book.getAuthor(), book.getPublishYear(), book.getClassNumber());
        return bookFromDB == null;
    }

    public void saveBook(Book book) {

                Assert.isTrue(bookNotExist(book), "This book is already exist!");
                Assert.notNull(book, "Book can't be null!");
                Assert.hasText(book.getAuthor(), "Author is empty!");
                Assert.hasText(book.getTitle(), "Title is empty!");
                Assert.isTrue(book.getPublishYear() > 0, "Invalid year of publication!");
                Assert.isTrue(book.getCount() > 0, "Count of books can't be less zero!");
                book.setEnable(true);
                bookRepository.save(book);
    }

    public void updateBook(Book book){

            Assert.notNull(book, "Book can't be null!");
            Assert.hasText(book.getAuthor(), "Author is empty!");
            Assert.hasText(book.getTitle(), "Title is empty!");
            Assert.isTrue(book.getPublishYear() > 0, "Invalid year of publication!");
            Assert.isTrue(book.getCount() >= 0, "Count of books can't be less zero!");
            bookRepository.save(book);
    }

    public List<Book> getAllBooks(boolean enable) {
        return bookRepository.findByEnable(enable);
    }

    public Book getOneBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }

//    public OperationStatus removeBook(int id) {
//        Optional<Book> opBook = bookRepository.findById(id);
//        if (opBook.isPresent()) {
//            Book book = opBook.get();
//            bookRepository.delete(book);
//            return new OperationStatus(true,"Book successfully deleted!");
//        }
//        return new OperationStatus(false, "This book in't exist!");
//    }

    public List<Pupil> getBookPupils(int id) {
        List<Pupil> pupils = new ArrayList<>();
        List<RecordCard> recordCards = recordCardRepository.findByBookIdAndReturnDate(id, null);
        for (RecordCard recordCard : recordCards){
            pupils.add(pupilService.getOnePupil(recordCard.getPupilId()));
        }
        return pupils;
    }

    public int getBookCount() {
        return (int)bookRepository.count();
    }

    public List<Book> getAllAvailableBooks(int pupilId) {
        List<Book> books = bookRepository.findByEnable(true);

        return books.stream().filter(book -> recordCardRepository.
                findByBookIdAndPupilIdAndReturnDate(book.getId(), pupilId, null) == null && book.getCount() > 0).
                collect(Collectors.toList());
    }

    public List<Book> findBooks(String title, String author, Integer classNumber){

        if (classNumber != 0) {
            return bookRepository.findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLikeAndClassNumber(title, author, classNumber);
        }

        return bookRepository.findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLike(title, author);
    }
}
