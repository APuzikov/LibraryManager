package ru.mera.lib.model;

import ru.mera.lib.entity.Book;

import java.util.List;

public class BookPagination {

    private List<Book> books;
    private int pageCount;

    public BookPagination(List<Book> books, int pageCount) {
        this.books = books;
        this.pageCount = pageCount;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
