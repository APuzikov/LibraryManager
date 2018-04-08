package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.service.PupilService;

import java.util.List;

@RestController
@RequestMapping("/api/v.1.0/pupils")
public class PupilController {

    @Autowired
    private PupilService pupilService;

    @PostMapping
    public ResponseEntity savePupil(@RequestBody Pupil pupil){
        return pupilService.savePupil(pupil);
    }

    @PutMapping
    public ResponseEntity updatePupil(@RequestBody Pupil pupil){
        return pupilService.updatePupil(pupil);
    }

    @PostMapping("/findPupils")
    public List<Pupil> findPupils(@RequestBody Pupil pupil){
        return pupilService.findPupils(pupil);
    }

    @GetMapping("/{id}")
    public Pupil getOnePupil (@PathVariable int id){
        return pupilService.getOnePupil(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removePupil(@PathVariable int id){
        return pupilService.removePupil(id);
    }

    //      возвращает все книги выданные ученику
//    @GetMapping("/getPupilBooks/{id}")
//    public List<Book> getPupilBooks(@PathVariable int id){
//        return pupilService.getPupilBooks(id);
//    }

    @GetMapping("/count")
    public int getPupilCount(){
        return pupilService.getPupilCount();
    }

//    @GetMapping("/getAllDisabledPupil")
//    public List<Pupil> getAllDisabledPupils(){
//        return pupilService.getAllPupils(false);
//    }
}