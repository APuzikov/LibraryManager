package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.repository.BookRepository;
import ru.mera.lib.entity.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public JsonResponse saveBook(Book book) {
        try {
            Assert.notNull(book, "Book can't be null!");
            Assert.hasText(book.getAuthor(), "Author is empty!");
            Assert.hasText(book.getTitle(), "Title is empty!");
            Assert.isTrue(book.getPublishYear() > 0, "Invalid year of publication!");
            Assert.isTrue(book.getCount() > 0, "Count of books can't be less zero!");
            bookRepository.save(book);
            return new JsonResponse(true,"Book successfully saved!");
        } catch (Exception e){
            return new JsonResponse(false,"Can't save book: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
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
}
