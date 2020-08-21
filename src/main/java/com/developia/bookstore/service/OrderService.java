package com.developia.bookstore.service;

import com.developia.bookstore.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    void create(Order order);
}
