package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.entity.Book;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.entity.RecordCard;
import ru.mera.lib.repository.BookRepository;
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
    private BookRepository bookRepository;

    @Autowired
    private RecordCardRepository recordCardRepository;

    private boolean pupilNotExist(Pupil pupil){
        Pupil pupilFromDB = pupilRepository.findByNameAndClassNumberAndClassName(pupil.getName(),
                pupil.getClassNumber(), pupil.getClassName());
        return pupilFromDB == null;
    }

    public JsonResponse savePupil(Pupil pupil){
        if (pupilNotExist(pupil)) {
            try {
                Assert.notNull(pupil, "Pupil can't be null!");
                Assert.hasText(pupil.getName(), "Name of pupil is empty!");
                Assert.isTrue(pupil.getClassNumber() > 0, "Invalid class number!");

                pupil.setEnable(true);
                pupilRepository.save(pupil);
                return new JsonResponse(true, "Pupil saved successfully!");
            } catch (Exception e) {
                return new JsonResponse(false, "Can't save pupil: " + e.getMessage());
            }
        }
        return new JsonResponse(false, "Pupil already exist!");
    }

    public JsonResponse updatePupil(Pupil pupil){
        try {
            Assert.notNull(pupil, "Pupil can't be null!");
            Assert.hasText(pupil.getName(), "Name of pupil is empty!");
            Assert.isTrue(pupil.getClassNumber() > 0, "Invalid class number!");

            pupilRepository.save(pupil);
            return new JsonResponse(true, "Pupil updated successfully!");
        } catch (Exception e) {
            return new JsonResponse(false, "Can't update pupil: " + e.getMessage());
        }
    }

    public List<Pupil> getAllPupils(){
        return pupilRepository.findByEnable(true);
        //return pupilRepository.findAll();
    }

    public Pupil getOnePupil(int id) {
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) return opPupil.get();
        return null;
    }

//    public JsonResponse removePupil(int id){
//        Optional<Pupil> opPupil = pupilRepository.findById(id);
//        if (opPupil.isPresent()){
//            Pupil pupil = opPupil.get();
//            pupilRepository.delete(pupil);
//            return new JsonResponse(true,"Pupil successfully deleted!");
//        }
//        return new JsonResponse(false, "This pupil isn't exist!");
//    }

    public JsonResponse activatePupil(int id){
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) {
            Pupil pupil = opPupil.get();
            pupil.setEnable(true);
            pupilRepository.save(pupil);
            return new JsonResponse(true, "Pupil successfully activated!");
        }
        return new JsonResponse(false, "This pupil isn't exist!");
    }

    public JsonResponse deactivatePupil(int id){
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) {
            Pupil pupil = opPupil.get();
            pupil.setEnable(false);
            pupilRepository.save(pupil);
            return new JsonResponse(true, "Pupil is inactive!");
        }
        return new JsonResponse(false, "This pupil isn't exist!");
    }

    public List<Book> getPupilBooks(int id) {
        List<Book> books = new ArrayList<>();
        List<RecordCard> recordCards = recordCardRepository.findByPupilIdAndReturnDate(id, null);
        for (RecordCard recordCard : recordCards){
            books.add(bookRepository.getOne(recordCard.getBookId()));
        }
        return books;
    }
}