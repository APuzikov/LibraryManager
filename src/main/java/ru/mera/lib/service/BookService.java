package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Book bookFromDB = bookRepository.findByTitleAndAuthorAndPublishYearAndClassNumber(book.getTitle(),
                book.getAuthor(), book.getPublishYear(), book.getClassNumber());
        return bookFromDB == null;
    }

    public ResponseEntity saveBook(Book book) {
        if(bookNotExist(book)) {
            try {
                Assert.notNull(book, "Book can't be null!");
                Assert.hasText(book.getAuthor(), "Author is empty!");
                Assert.hasText(book.getTitle(), "Title is empty!");
                Assert.isTrue(book.getPublishYear() > 0, "Invalid year of publication!");
                Assert.isTrue(book.getCount() > 0, "Count of books can't be less zero!");
                book.setEnable(true);
                bookRepository.save(book);
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity updateBook(Book book){
        try {
            Assert.notNull(book, "Book can't be null!");
            Assert.hasText(book.getAuthor(), "Author is empty!");
            Assert.hasText(book.getTitle(), "Title is empty!");
            Assert.isTrue(book.getPublishYear() > 0, "Invalid year of publication!");
            Assert.isTrue(book.getCount() >= 0, "Count of books can't be less zero!");
            bookRepository.save(book);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
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
            return bookRepository.findByTitleLikeAndAuthorLikeAndClassNumberAndEnable(title, author, classNumber, true);
        }

        return bookRepository.findByTitleLikeAndAuthorLikeAndEnable(title, author, true);
    }
}
