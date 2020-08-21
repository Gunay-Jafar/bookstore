package com.developia.bookstore.service.impl;

import com.developia.bookstore.model.Book;
import com.developia.bookstore.model.Review;
import com.developia.bookstore.model.User;
import com.developia.bookstore.repository.BookRepository;
import com.developia.bookstore.service.BookService;
import com.developia.bookstore.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final SessionService sessionService;

    public BookServiceImpl(BookRepository bookRepository, SessionService sessionService) {
        this.bookRepository = bookRepository;
        this.sessionService = sessionService;
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public void create(Book book) {
        log.debug("create book: {}", book);
        bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        Book oldBook = bookRepository.findByIsbn(book.getIsbn());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setDescription(book.getDescription());
        oldBook.setName(book.getName());
        oldBook.setPageSize(book.getPageSize());
        oldBook.setPrice(book.getPrice());
        oldBook.setPublishDate(book.getPublishDate());
        bookRepository.save(oldBook);
    }

    @Override
    public void delete(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        bookRepository.delete(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void review(String isbn, Review review) {



        if (review.getRating() == null || review.getComment() == null || review.getComment().isEmpty())
            return;

        if (review.getRating().compareTo(BigDecimal.valueOf(1)) < 0 || review.getRating()
                .compareTo(BigDecimal.valueOf(5)) > 0) return;

        Book book = findByIsbn(isbn);
        User user = sessionService.findActiveSession().getUser();

        review.setBook(book);
        review.setUser(user);

        List<Review> reviews = book.getReviews();
        reviews.add(review);

        bookRepository.save(book);
    }
}
