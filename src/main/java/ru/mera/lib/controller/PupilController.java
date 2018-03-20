package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.entity.Book;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.service.PupilService;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v.1.0")
public class PupilController {

    @Autowired
    private PupilService pupilService;

    @PostMapping("/createPupil")
    public JsonResponse savePupil(@RequestBody Pupil pupil){
        return pupilService.savePupil(pupil);
    }

    @PostMapping("/updatePupil/{id}")
    public JsonResponse updatePupil(@PathVariable int id, @RequestBody Pupil pupil){
        pupil.setId(id);
        return pupilService.updatePupil(pupil);
    }

    @GetMapping("/getAllPupil")
    public List<Pupil> getAllPupils(){
        return pupilService.getAllPupils(true);
    }

    @GetMapping("/getpupil/{id}")
    public Pupil getOnePupil (@PathVariable int id){
        return pupilService.getOnePupil(id);
    }

//    @DeleteMapping("/deletePupil/{id}")
//    public JsonResponse removePupil(@PathVariable int id){
//        return pupilService.removePupil(id);
//    }

    @GetMapping("/activatePupil/{id}")
    public JsonResponse activatePupil(@PathVariable int id){
        return pupilService.activatePupil(id);
    }

    @GetMapping("/deactivatePupil/{id}")
    public JsonResponse deactivatePupil(@PathVariable int id){
        return pupilService.deactivatePupil(id);
    }

    //      возвращает все книги выданные ученику
    @GetMapping("/getPupilBooks/{id}")
    public List<Book> getPupilBooks(@PathVariable int id){
        return pupilService.getPupilBooks(id);
    }

    @GetMapping("/getPupilCount")
    public int getPupilCount(){
        return pupilService.getPupilCount();
    }

    @GetMapping("/getAllDisabledPupil")
    public List<Pupil> getAllDisabledPupils(){
        return pupilService.getAllPupils(false);
    }

}
