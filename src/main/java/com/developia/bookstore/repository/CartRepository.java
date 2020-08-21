package com.developia.bookstore.repository;

import com.developia.bookstore.model.Cart;
import com.developia.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
