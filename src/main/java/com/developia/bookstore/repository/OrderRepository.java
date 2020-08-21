package com.developia.bookstore.repository;

import com.developia.bookstore.model.Order;
import com.developia.bookstore.model.Review;
import com.developia.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
