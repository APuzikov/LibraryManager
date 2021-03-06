package ru.mera.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mera.lib.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    Book findByTitleAndAuthorAndPublishYearAndClassNumber(String title, String author, int publishYear, int classNumber);
    List<Book> findByEnable(boolean enable);
}
