package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.OperationStatus;
import ru.mera.lib.entity.Book;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.service.BookService;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v.1.0")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping()
    public OperationStatus saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public OperationStatus updateBook(@RequestBody Book book, @PathVariable int id){
        book.setId(id);
        return bookService.updateBook(book);
    }

    @GetMapping
    public List<Book> findBooks(@RequestParam(name = "title", required = false) String title ,
                                @RequestParam(name = "author", required = false) String author,
                                @RequestParam(name = "classNumber", required = false) Integer classNumber){
        return bookService.findBooks(title, author, classNumber);
    }

//    @GetMapping
//    public List<Book> getAllBooks(@RequestParam(name = "enable", required = false) boolean enable){
//        return bookService.getAllBooks(enable);
//    }

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

//    @GetMapping("/getAllDisabledBooks")
//    public List<Book> getAllDisabledBooks(){
//        return bookService.getAllBooks(false);
//    }
//
//    @GetMapping("/activateBook/{id}")
//    public OperationStatus activateBook(@PathVariable int id){
//        return bookService.activateBook(id);
//    }
//
//    @GetMapping("/deactivateBook/{id}")
//    public OperationStatus deactivateBook(@PathVariable int id){
//        return bookService.deactivateBook(id);
//    }
}