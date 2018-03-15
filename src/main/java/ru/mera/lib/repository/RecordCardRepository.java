package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mera.lib.entity.RecordCard;

import java.util.List;

@Repository
public interface RecordCardRepository extends JpaRepository<RecordCard, Integer>{
    List<RecordCard> findByBookIdAndPupilId(int bookId, int pupilId);
    RecordCard findByBookIdAndPupilIdAndReturnDate(int bookId, int pupilId, String returnDate);
}
