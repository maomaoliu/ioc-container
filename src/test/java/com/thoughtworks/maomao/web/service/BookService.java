package com.thoughtworks.maomao.web.service;

import com.thoughtworks.maomao.web.model.Book;

import java.util.List;

public interface BookService {
    Book getBook(Integer id);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Integer id);

    List<Book> getAllBooks();
}
