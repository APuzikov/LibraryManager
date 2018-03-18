package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.service.RecordCardService;

@CrossOrigin(origins = "http://localhost:3000")
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