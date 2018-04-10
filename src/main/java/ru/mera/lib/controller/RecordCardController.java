package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.service.RecordCardService;

@RestController
@RequestMapping("/api/v.1.0/recordCard")
public class RecordCardController {

    @Autowired
    private RecordCardService recordCardService;

    @PostMapping("/giveBook/{bookId}/{pupilId}")
    public ResponseEntity giveBook(@PathVariable int bookId, @PathVariable int pupilId){
        return recordCardService.giveBook(bookId, pupilId);
    }

    @PutMapping("/returnBook/{bookId}/{pupilId}")
    public ResponseEntity returnBook(@PathVariable int bookId, @PathVariable int pupilId){
        return recordCardService.returnBook(bookId, pupilId);
    }

}