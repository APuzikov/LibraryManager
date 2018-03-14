package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.service.PupilService;

import java.util.List;

@RestController
@RequestMapping("/pupil")
public class PupilController {

    @Autowired
    private PupilService pupilService;

    @PostMapping("/save")
    public String savePupil(@RequestBody Pupil pupil){
        return pupilService.savePupil(pupil);
    }

    @GetMapping("/all")
    public List<Pupil> getAllPupils(){
        return pupilService.getAllPupils();
    }

    @GetMapping("{id}")
    public Pupil getOnePupil (@PathVariable int id){
        return pupilService.getOnePupil(id);
    }

    @GetMapping("/remove/{id}")
    public String removePupil(@PathVariable int id){
        return pupilService.removePupil(id);
    }

    @GetMapping("/activate/{id}")
    public String activatePupil(@PathVariable int id){
        return pupilService.activatePupil(id);
    }

    @GetMapping("/deactivate/{id}")
    public String deactivatePupil(@PathVariable int id){
        return pupilService.deactivatePupil(id);
    }

}
