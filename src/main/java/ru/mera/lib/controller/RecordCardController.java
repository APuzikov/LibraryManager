package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.service.RecordCardService;

@RestController
@RequestMapping("/api/v.1.0")
public class RecordCardController {

    @Autowired
    private RecordCardService recordCardService;

    @GetMapping("/giveBook/{bookId}/{pupilId}")
    public JsonResponse giveBook(@PathVariable int bookId, @PathVariable int pupilId){
        return recordCardService.giveBook(bookId, pupilId);
    }

    @GetMapping("/returnBook/{bookId}/{pupilId}")
    public JsonResponse returnBook(@PathVariable int bookId, @PathVariable int pupilId){
        return recordCardService.returnBook(bookId, pupilId);
    }
}