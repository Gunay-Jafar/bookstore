package com.developia.bookstore.repository;

import com.developia.bookstore.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
