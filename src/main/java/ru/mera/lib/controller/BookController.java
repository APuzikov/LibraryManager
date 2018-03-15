package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.entity.Book;
import ru.mera.lib.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v.1.0")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/createBook")
    public JsonResponse saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @PutMapping("/updateBook/{id}")
    public JsonResponse updateBook(@RequestBody Book book, @PathVariable int id){
        book.setId(id);
        return bookService.saveBook(book);
    }

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/getBook/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }

    @DeleteMapping("/deleteBook/{id}")
    public JsonResponse removeBook(@PathVariable int id){
        return bookService.removeBook(id);
    }
}