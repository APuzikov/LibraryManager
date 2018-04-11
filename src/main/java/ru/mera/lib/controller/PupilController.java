package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.model.PupilPagination;
import ru.mera.lib.service.PupilService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v.1.0/pupils")
public class PupilController {

    @Autowired
    private PupilService pupilService;

    @PostMapping
    public ResponseEntity savePupil(@RequestBody Pupil pupil, HttpServletResponse response) throws IOException {

        try {
            pupilService.savePupil(pupil);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity updatePupil(@RequestBody Pupil pupil, HttpServletResponse response) throws IOException {
        try {
            pupilService.updatePupil(pupil);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public PupilPagination findPupils(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer classNumber,
                                      @RequestParam(required = false) String className,
                                      @RequestParam(required = false) Integer page){

        if (name == null) name = "";
        if (classNumber == null) classNumber = 0;
        if (page == null) page = 1;

        List<Pupil> pupils = pupilService.findPupils("%" + name + "%", classNumber, className);

        pupils.sort(Comparator.comparing(Pupil::getName));//   сортировка по-имени

        int listSize = pupils.size();
        int pageCount = listSize/10 + 1;
        int lastIndex;

        if (page > pageCount) return new PupilPagination(Collections.emptyList(), 1);

        if (!pupils.isEmpty() && listSize > 10) {
            int firstIndex = (page - 1) * 10;

            if (listSize > (page - 1) * 10 + 10) {
                lastIndex = (page - 1) * 10 + 10;
            } else lastIndex = (page - 1) * 10 + listSize % 10;
            return new PupilPagination(pupils.subList(firstIndex, lastIndex), pageCount);

        } else return new PupilPagination(pupils, pageCount);

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

}