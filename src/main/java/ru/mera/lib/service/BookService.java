package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.entity.RecordCard;
import ru.mera.lib.repository.BookRepository;
import ru.mera.lib.entity.Book;
import ru.mera.lib.repository.RecordCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public JsonResponse saveBook(Book book) {
        if(bookNotExist(book)) {
            try {
                Assert.notNull(book, "Book can't be null!");
                Assert.hasText(book.getAuthor(), "Author is empty!");
                Assert.hasText(book.getTitle(), "Title is empty!");
                Assert.isTrue(book.getPublishYear() > 0, "Invalid year of publication!");
                Assert.isTrue(book.getCount() > 0, "Count of books can't be less zero!");
                book.setEnable(true);
                bookRepository.save(book);
                return new JsonResponse(true, "Book successfully saved!");
            } catch (Exception e) {
                return new JsonResponse(false, "Can't save book: " + e.getMessage());
            }
        } return new JsonResponse(false, "Book already exist!");
    }

    public JsonResponse updateBook(Book book){
        try {
            Assert.notNull(book, "Book can't be null!");
            Assert.hasText(book.getAuthor(), "Author is empty!");
            Assert.hasText(book.getTitle(), "Title is empty!");
            Assert.isTrue(book.getPublishYear() > 0, "Invalid year of publication!");
            Assert.isTrue(book.getCount() >= 0, "Count of books can't be less zero!");
            bookRepository.save(book);
            return new JsonResponse(true, "Book successfully updated!");
        } catch (Exception e) {
            return new JsonResponse(false, "Can't update book: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks(boolean enable) {
        return bookRepository.findByEnable(enable);
    }

    public Book getOneBook(int id) {
        Optional<Book> opBook = bookRepository.findById(id);
        if (opBook.isPresent()) return opBook.get();
        return null;
    }

    public JsonResponse removeBook(int id) {
        Optional<Book> opBook = bookRepository.findById(id);
        if (opBook.isPresent()) {
            Book book = opBook.get();
            bookRepository.delete(book);
            return new JsonResponse(true,"Book successfully deleted!");
        }
        return new JsonResponse(false, "This book in't exist!");
    }

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

    public JsonResponse activateBook(int id) {
        Optional<Book> opBook = bookRepository.findById(id);
        if (opBook.isPresent()){
            Book book = opBook.get();
            book.setEnable(true);
            bookRepository.save(book);
            return new JsonResponse(true, "Book successfully activated!");
        }
        return new JsonResponse(false, "This book isn't exist!");
    }

    public JsonResponse deactivateBook(int id) {
        Optional<Book> opBook = bookRepository.findById(id);
        if (opBook.isPresent()){
            Book book = opBook.get();
            book.setEnable(false);
            bookRepository.save(book);
            return new JsonResponse(true, "Book is inactive!");
        }
        return new JsonResponse(false, "This book isn't exist!");
    }

    public List<Book> getAllAvailableBooks(int pupilId) {
        List<Book> books = bookRepository.findByEnable(true);
        List<Book> avBooks = new ArrayList<>();

        for (Book book : books){
            if (recordCardRepository.findByBookIdAndPupilIdAndReturnDate(book.getId(), pupilId, null) == null){
                avBooks.add(book);
            }
        }
        return avBooks;
    }
}
