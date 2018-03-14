package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.entity.Pupil;
import ru.mera.lib.repository.PupilRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PupilService {

    @Autowired
    private PupilRepository pupilRepository;

    public String savePupil(Pupil pupil){
        try {
            Assert.notNull(pupil, "Pupil can't be null!");
            Assert.hasText(pupil.getName(), "Name of pupil is empty!");
            Assert.isTrue(pupil.getClassNumber() > 0, "Invalid class number!");
            pupilRepository.save(pupil);
            return "Pupil saved successfully!";
        } catch (Exception e){
            return "Can't save pupil: " + e.getMessage();
        }
    }

    public List<Pupil> getAllPupils(){
        return pupilRepository.findAll();
    }

    public Pupil getOnePupil(int id) {
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) return opPupil.get();
        return null;
    }

    public String removePupil(int id){
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()){
            Pupil pupil = opPupil.get();
            pupilRepository.delete(pupil);
            return "Pupil successfully deleted!";
        }

        return "This pupil isn't exist!";
    }

    public String activatePupil(int id){
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) {
            Pupil pupil = opPupil.get();
            pupil.setEnable(true);
            pupilRepository.save(pupil);
            return "Pupil successfully activated!";
        }
        return "This pupil isn't exist!";
    }

    public String deactivatePupil(int id){
        Optional<Pupil> opPupil = pupilRepository.findById(id);
        if (opPupil.isPresent()) {
            Pupil pupil = opPupil.get();
            pupil.setEnable(false);
            pupilRepository.save(pupil);
            return "Pupil is inactive!";
        }
        return "This pupil isn't exist!";
    }
}