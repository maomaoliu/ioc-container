package com.thoughtworks.maomao.web.controller;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.web.annotation.Controller;
import com.thoughtworks.maomao.web.model.Book;
import com.thoughtworks.maomao.web.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller(model = Book.class)
public class BookController extends NoamController {

    @Glue
    private BookService bookService;

    @Override
    public List index(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> params) {
        return bookService.getAllBooks();
    }

    @Override
    public Book show(Integer id, HttpServletRequest req, HttpServletResponse resp, Map<String, Object> params) {
        return bookService.getBook(id);
    }

    @Override
    public void delete(Integer id, HttpServletRequest req, HttpServletResponse resp, Map<String, Object> params) {
        bookService.deleteBook(id);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
