package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mera.lib.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitleAndAuthorAndPublishYearAndClassNumber(String title, String author, int publishYear, int classNumber);

    List<Book> findByEnable(boolean enable);

    List<Book> findByTitleAndEnable(String title, boolean enable);

    List<Book> findByTitleAndAuthorAndEnable(String title, String author, boolean enable);

    List<Book> findByTitleAndAuthorAndClassNumberAndEnable(String title, String author, int classNumber, boolean enable);

    List<Book> findByTitleAndClassNumberAndEnable(String title, int classNumber, boolean enable);

    List<Book> findByClassNumberAndEnable(int classNumber, boolean enable);

    List<Book> findByAuthorAndEnable(String author, boolean enable);

    List<Book> findByAuthorAndClassNumberAndEnable(String author, int classNumber, boolean enable);
}
