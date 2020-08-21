package com.developia.bookstore.service.impl;

import com.developia.bookstore.model.*;
import com.developia.bookstore.repository.OrderRepository;
import com.developia.bookstore.service.EmailService;
import com.developia.bookstore.service.OrderService;
import com.developia.bookstore.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final SessionService sessionService;
    private final EmailService emailService;

    public OrderServiceImpl(OrderRepository orderRepository, SessionService sessionService, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.sessionService = sessionService;
        this.emailService = emailService;
    }

    @Override
    public List<Order> findAll() {
        Session session = sessionService.findActiveSession();
        if (session == null) return Collections.emptyList();
        User user = session.getUser();
        return orderRepository.findAllByUser(user);
    }

    @Override
    public void create(Order order) {

        logger.info("Order create start");

        orderRepository.save(order);

        StringBuilder message = new StringBuilder();
        for (Book book : order.getBooks())
            message.append(" ")
                    .append(book.getIsbn())
                    .append(" ")
                    .append(book.getName())
                    .append("\n");

        OrderEmail orderEmail = OrderEmail
                .builder()
                .orderNumber(order.getOrderNumber())
                .message(message.toString())
                .build();

        logger.info("Order create end");

        emailService.sendOrderEmail(orderEmail);
    }
}
