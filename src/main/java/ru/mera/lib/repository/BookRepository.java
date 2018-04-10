package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mera.lib.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitleIgnoreCaseAndAuthorIgnoreCaseAndPublishYearAndClassNumber(String title, String author, int publishYear, int classNumber);

    List<Book> findByEnable(boolean enable);

    List<Book> findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLikeAndEnable(String title, String author, boolean enable);

    List<Book> findByTitleIgnoreCaseLikeAndAuthorIgnoreCaseLikeAndClassNumberAndEnable(String title, String author, Integer classNumber, boolean enable);
}
