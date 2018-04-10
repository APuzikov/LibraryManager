package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mera.lib.entity.Pupil;

import java.util.List;

@Repository
public interface PupilRepository extends JpaRepository<Pupil, Integer>{
    List<Pupil> findByEnable(boolean enable);

    Pupil findByNameIgnoreCaseAndClassNumberAndClassNameIgnoreCase(String name, int classNumber, String classname);

    List<Pupil> findByNameIgnoreCaseLikeAndClassNumberAndEnable(String name, Integer classNumber, boolean enable);

    List<Pupil> findByNameIgnoreCaseLikeAndClassNameIgnoreCaseAndEnable(String name, String className, boolean enable);

    List<Pupil> findByNameIgnoreCaseLikeAndEnable(String name, boolean enable);
}
