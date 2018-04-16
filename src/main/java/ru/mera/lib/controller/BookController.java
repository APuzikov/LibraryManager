package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.Book;
import ru.mera.lib.model.BookPagination;
import ru.mera.lib.repository.BookRepository;
import ru.mera.lib.service.BookService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v.1.0/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ResponseEntity saveBook(@RequestBody Book book, HttpServletResponse response) throws IOException {
        try {
            bookService.saveBook(book);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity updateBook(@RequestBody Book book, HttpServletResponse response) throws IOException {
        try {
            bookService.updateBook(book);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public BookPagination findBooks(@RequestParam(name = "title", required = false) String title , //publishYear
                                    @RequestParam(name = "author", required = false) String author,
                                    @RequestParam(name = "classNumber", required = false) Integer classNumber,
                                    @RequestParam(name = "page", required = false) Integer page,
                                    @RequestParam(name = "publishYear", required = false) Integer publishYear){

        if (title == null) title = "";
        if (author == null) author = "";
        if (classNumber == null) classNumber = 0;
        if (publishYear == null) publishYear = 0;
        if (page == null) page = 1;

        List<Book> books = bookService.findBooks("%" + title + "%", "%" + author + "%", classNumber, publishYear);
        books.sort((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));

        int totalItems = books.size();
        int listSize = books.size();
        int pageCount;
        int lastIndex;

        if (listSize%10 == 0){
            pageCount = listSize/10;
        } else pageCount = listSize/10 + 1;

        if (page > pageCount) return new BookPagination(Collections.emptyList(), 1, totalItems);

        if (!books.isEmpty() && listSize > 10) {
            int firstIndex = (page - 1) * 10;

            if (listSize >= (page - 1) * 10 + 10) {
                lastIndex = (page - 1) * 10 + 10;
            } else lastIndex = (page - 1) * 10 + listSize % 10;
            return new BookPagination(books.subList(firstIndex, lastIndex), pageCount, totalItems);

        } else return new BookPagination(books, pageCount, totalItems);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable int id, HttpServletResponse response) throws IOException {
        try {
            bookService.deleteBook(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }

    @GetMapping("/count")
    public int getBookCount(){
        return bookService.getBookCount();
    }
}