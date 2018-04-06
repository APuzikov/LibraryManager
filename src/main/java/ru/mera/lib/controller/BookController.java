package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.Book;
import ru.mera.lib.repository.BookRepository;
import ru.mera.lib.service.BookService;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v.1.0/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ResponseEntity saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable int id){
        book.setId(id);
        return bookService.updateBook(book);
    }

    @GetMapping("/search")
    public List<Book> findBooks(@RequestParam(name = "title", required = false) String title ,
                                @RequestParam(name = "author", required = false) String author,
                                @RequestParam(name = "classNumber", required = false) Integer classNumber){

        if (title == null) title = "";
        if (author == null) author = "";
        if (classNumber == null) classNumber = 0;

        return bookService.findBooks("%" + title + "%", "%" + author + "%", classNumber);
    }

    @GetMapping("/getAllAvailableBooks/{pupilId}")
    public List<Book> getAllAvailableBooks (@PathVariable int pupilId){
        return bookService.getAllAvailableBooks(pupilId);
    }

    @PostMapping("/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }

//    @DeleteMapping("/deleteBook/{id}")
//    public OperationStatus removeBook(@PathVariable int id){
//        return bookService.removeBook(id);
//    }

    //возвращает всех учеников, которым выдана эта книга
//    @GetMapping("/search/{id}")
//    public List<Pupil> getAllPupilsForBook(@PathVariable int bookId){
//        return bookService.getBookPupils(bookId);
//    }

    @GetMapping("/count")
    public int getBookCount(){
        return bookService.getBookCount();
    }

    @GetMapping("/like")
    public List<Book> likeTitle(@RequestParam String title){
        return bookRepository.findByTitleLikeAndEnable("%" + title + "%", true);
    }
}