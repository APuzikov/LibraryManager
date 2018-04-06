package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.OperationStatus;
import ru.mera.lib.entity.Book;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.entity.RecordCard;
import ru.mera.lib.repository.PupilRepository;
import ru.mera.lib.repository.RecordCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PupilService {

    @Autowired
    private PupilRepository pupilRepository;

    @Autowired
    private RecordCardRepository recordCardRepository;

    @Autowired
    private BookService bookService;

    private boolean pupilNotExist(Pupil pupil){
        Pupil pupilFromDB = pupilRepository.findByNameAndClassNumberAndClassName(pupil.getName(),
                pupil.getClassNumber(), pupil.getClassName());
        return pupilFromDB == null;
    }

    public ResponseEntity savePupil(Pupil pupil){
        if (pupilNotExist(pupil)) {
            try {
                Assert.notNull(pupil, "Pupil can't be null!");
                Assert.hasText(pupil.getName(), "Name of pupil is empty!");
                Assert.isTrue(pupil.getClassNumber() > 0, "Invalid class number!");

                pupil.setEnable(true);
                pupilRepository.save(pupil);
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity updatePupil(Pupil pupil){
        try {
            Assert.notNull(pupil, "Pupil can't be null!");
            Assert.hasText(pupil.getName(), "Name of pupil is empty!");
            Assert.isTrue(pupil.getClassNumber() > 0, "Invalid class number!");

            pupilRepository.save(pupil);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Pupil> getAllPupils(boolean enable){
        return pupilRepository.findByEnable(enable);
        //return pupilRepository.findAll();
    }

    public Pupil getOnePupil(int id) {
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) return opPupil.get();
        return null;
    }

//    public OperationStatus removePupil(int id){
//        Optional<Pupil> opPupil = pupilRepository.findById(id);
//        if (opPupil.isPresent()){
//            Pupil pupil = opPupil.get();
//            pupilRepository.delete(pupil);
//            return new OperationStatus(true,"Pupil successfully deleted!");
//        }
//        return new OperationStatus(false, "This pupil isn't exist!");
//    }

    public OperationStatus activatePupil(int id){
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) {
            Pupil pupil = opPupil.get();
            pupil.setEnable(true);
            pupilRepository.save(pupil);
            return new OperationStatus(true, "Pupil successfully activated!");
        }
        return new OperationStatus(false, "This pupil isn't exist!");
    }

    public OperationStatus deactivatePupil(int id){
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) {
            Pupil pupil = opPupil.get();
            pupil.setEnable(false);
            pupilRepository.save(pupil);
            return new OperationStatus(true, "Pupil is inactive!");
        }
        return new OperationStatus(false, "This pupil isn't exist!");
    }

    public List<Book> getPupilBooks(int id) {
        List<Book> books = new ArrayList<>();
        List<RecordCard> recordCards = recordCardRepository.findByPupilIdAndReturnDate(id, null);
        for (RecordCard recordCard : recordCards){
            books.add(bookService.getOneBook(recordCard.getBookId()));
        }
        return books;
    }

    public int getPupilCount() {
        return pupilRepository.findByEnable(true).size();
    }

    public List<Pupil> findPupils(Pupil pupil){
        if (pupil.getClassNumber() == 0 &&
                pupil.getClassName() == null &&
                pupil.getName() != null){
            return pupilRepository.findByNameAndEnable(pupil.getName(), true);
        }

        if (pupil.getClassNumber() == 0 &&
                pupil.getClassName() != null &&
                pupil.getName() != null){
            return pupilRepository.findByNameAndClassNameAndEnable(pupil.getName(), pupil.getClassName(), true);
        }

        if (pupil.getClassNumber() != 0 &&
                pupil.getClassName() != null &&
                pupil.getName() == null){
            return pupilRepository.findByClassNumberAndClassNameAndEnable(pupil.getClassNumber(), pupil.getClassName(), true);
        }

        if (pupil.getClassNumber() == 0 &&
                pupil.getClassName() != null &&
                pupil.getName() != null){
            return pupilRepository.findByNameAndClassNameAndEnable(pupil.getName(), pupil.getClassName(),true);
        }

        if (pupil.getClassNumber() != 0 &&
                pupil.getClassName() == null &&
                pupil.getName() != null){
            return pupilRepository.findByNameAndClassNumberAndEnable(pupil.getName(), pupil.getClassNumber(),true);
        }

        if (pupil.getClassNumber() != 0 &&
                pupil.getClassName() == null &&
                pupil.getName() == null){
            return pupilRepository.findByClassNumberAndEnable(pupil.getClassNumber(), true);
        }

        if (pupil.getClassNumber() == 0 &&
                pupil.getClassName() != null &&
                pupil.getName() == null){
            return pupilRepository.findByClassNameAndEnable(pupil.getClassName(), true);
        }

        return null;
    }

    public ResponseEntity removePupil(int id) {
        Optional<Pupil> pupil = pupilRepository.findById(id);
        try {
            pupil.ifPresent(p -> pupilRepository.delete(p));
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}