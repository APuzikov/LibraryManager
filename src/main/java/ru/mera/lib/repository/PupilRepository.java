package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mera.lib.entity.Pupil;

import java.util.List;

@Repository
public interface PupilRepository extends JpaRepository<Pupil, Integer>{
    List<Pupil> findByEnable(boolean enable);

    Pupil findByNameAndClassNumberAndClassName(String name, int classNumber, String classname);

    List<Pupil> findByNameAndEnable(String name, boolean enable);

    List<Pupil> findByNameAndClassNameAndEnable(String name, String className, boolean enable);

    List<Pupil> findByNameAndClassNameAndClassNumberAndEnable(String name, String className, int classNumber, boolean enable);

    List<Pupil> findByClassNumberAndClassNameAndEnable(int classNumber, String className, boolean enable);

    List<Pupil> findByNameAndClassNumberAndEnable(String name, int classNumber, boolean enable);

    List<Pupil> findByClassNumberAndEnable(int classNumber, boolean enable);

    List<Pupil> findByClassNameAndEnable(String className, boolean enable);
}
