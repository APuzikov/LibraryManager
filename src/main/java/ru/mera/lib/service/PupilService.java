package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
        Pupil pupilFromDB = pupilRepository.findByNameIgnoreCaseAndClassNumberAndClassNameIgnoreCase(pupil.getName(),
                pupil.getClassNumber(), pupil.getClassName());
        return pupilFromDB == null;
    }

    public void savePupil(Pupil pupil){
                Assert.isTrue(pupilNotExist(pupil), "This pupil is already exist!");
                Assert.notNull(pupil, "Pupil can't be null!");
                Assert.hasText(pupil.getName(), "Name of pupil is empty!");
                Assert.isTrue(pupil.getClassNumber() > 0, "Invalid class number!");
                pupilRepository.save(pupil);
    }

    public void updatePupil(Pupil pupil){
            Assert.notNull(pupil, "Pupil can't be null!");
            Assert.hasText(pupil.getName(), "Name of pupil is empty!");
            Assert.isTrue(pupil.getClassNumber() > 0, "Invalid class number!");

            pupilRepository.save(pupil);
    }

//    public List<Pupil> getAllPupils(boolean enable){
//        return pupilRepository.findByEnable(enable);
//        //return pupilRepository.findAll();
//    }

    public Pupil getOnePupil(int id) {
        return pupilRepository.findById(id).orElse(null);
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


    public List<Pupil> findPupils(String name, Integer classNumber, String className){
        if (className == null && classNumber == 0){
            return pupilRepository.findByNameIgnoreCaseLike(name);
        }

        if (className == null){
            return pupilRepository.findByNameIgnoreCaseLikeAndClassNumber(name, classNumber);
        }

        return pupilRepository.findByNameIgnoreCaseLikeAndClassNameIgnoreCase(name, className);
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

    public void deletePupil(int id) {
        Optional<Pupil> opPupil = pupilRepository.findById(id);

        if (opPupil.isPresent()){
            Pupil pupil = opPupil.get();
            List<RecordCard> recordCards = recordCardRepository.findByPupilIdAndReturnDate(pupil.getId(), null);
            Assert.isTrue(recordCards.isEmpty(), "This pupil did not return the book!");
            pupilRepository.delete(pupil);
        } else throw new IllegalArgumentException("This pupil is not exist!");
    }
}