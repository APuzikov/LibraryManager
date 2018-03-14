package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mera.lib.entity.Pupil;

import java.util.List;

public interface PupilRepository extends JpaRepository<Pupil, Integer>{
    List<Pupil> findByName(String name);
}
