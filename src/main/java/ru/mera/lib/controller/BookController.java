package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.JsonResponse;
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

    @PostMapping("/createBook")
    public JsonResponse saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @PostMapping("/updateBook/{id}")
    public JsonResponse updateBook(@RequestBody Book book, @PathVariable int id){
        book.setId(id);
        return bookService.updateBook(book);
    }

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks(true);
    }

    @GetMapping("/getAllAvailableBooks/{pupilId}")
    public List<Book> getAllAvailableBooks (@PathVariable int pupilId){
        return bookService.getAllAvailableBooks(pupilId);
    }

    @GetMapping("/getBook/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }

//    @DeleteMapping("/deleteBook/{id}")
//    public JsonResponse removeBook(@PathVariable int id){
//        return bookService.removeBook(id);
//    }

    //возвращает всех учеников, которым выдана эта книга
    @GetMapping("/getBookPupils/{id}")
    public List<Pupil> getBookPupils(@PathVariable int id){
        return bookService.getBookPupils(id);
    }

    @GetMapping("/getBookCount")
    public int getBookCount(){
        return bookService.getBookCount();
    }

    @GetMapping("/getAllDisabledBooks")
    public List<Book> getAllDisabledBooks(){
        return bookService.getAllBooks(false);
    }

    @GetMapping("/activateBook/{id}")
    public JsonResponse activateBook(@PathVariable int id){
        return bookService.activateBook(id);
    }

    @GetMapping("/deactivateBook/{id}")
    public JsonResponse deactivateBook(@PathVariable int id){
        return bookService.deactivateBook(id);
    }
}