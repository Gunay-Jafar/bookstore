package com.developia.bookstore.service;

import com.developia.bookstore.model.Book;
import com.developia.bookstore.model.Card;
import com.developia.bookstore.model.Review;

import java.util.List;

public interface BookService {

    Book findByIsbn(String isbn);

    void create(Book book);

    void update(Book book);

    void delete(String isbn);

    List<Book> findAll();

    void review(String isbn, Review review);
}
