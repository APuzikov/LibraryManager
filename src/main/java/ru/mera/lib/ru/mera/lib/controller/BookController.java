package ru.mera.lib.ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.ru.mera.lib.entity.Book;
import ru.mera.lib.ru.mera.lib.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public String saveBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getOneBook(@PathVariable int id){
        return bookService.getOneBook(id);
    }

    @GetMapping("/remove/{id}")
    public String removeBook(@PathVariable int id){
        return bookService.removeBook(id);
    }
}