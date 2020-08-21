package com.developia.bookstore.service.impl;

import com.developia.bookstore.model.Book;
import com.developia.bookstore.model.Card;
import com.developia.bookstore.model.Cart;
import com.developia.bookstore.model.Order;
import com.developia.bookstore.model.Session;
import com.developia.bookstore.model.User;
import com.developia.bookstore.repository.CartRepository;
import com.developia.bookstore.service.BookService;
import com.developia.bookstore.service.CartService;
import com.developia.bookstore.service.OrderService;
import com.developia.bookstore.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final SessionService sessionService;
    private final CartRepository cartRepository;
    private final BookService bookService;
    private final OrderService orderService;

    public CartServiceImpl(SessionService sessionService,
                           CartRepository cartRepository, BookService bookService,
                           OrderService orderService) {
        this.sessionService = sessionService;
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @Override
    public Cart findActiveCart() {
        Session session = sessionService.findActiveSession();
        if (session == null) return Cart.builder().build();
        User user = session.getUser();
        Cart cart = cartRepository.findByUser(user);
        return cart;
    }

    @Override
    public void addBook(String isbn) {
        log.info("addBook start");
        Session session = sessionService.findActiveSession();
        if (session == null) return;

        User user = session.getUser();
        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        Book book = bookService.findByIsbn(isbn);
        List<Book> booksInCart = cart.getBooks();
        booksInCart.add(book);

        cartRepository.save(cart);

        log.info("addBook end");
    }

    @Override
    public void removeBook(String isbn) {

        Cart cart = findActiveCart();
        List<Book> books = cart.getBooks();
        List<Book> newList = new ArrayList<>();

        for (Book book : books)
            if (!book.getIsbn().equals(isbn))
                newList.add(book);

        cart.setBooks(newList);
        cartRepository.save(cart);
    }

    @Override
    public void checkout(Card card) {

        User user = sessionService.findActiveSession().getUser();

        Cart cart = findActiveCart();
        List<Book> books = new ArrayList<>(cart.getBooks());

        Order order = Order
                .builder()
                .orderNumber(UUID.randomUUID().toString())
                .books(books)
                .dateTime(LocalDateTime.now())
                .user(user)
                .build();

        orderService.create(order);
    }
}