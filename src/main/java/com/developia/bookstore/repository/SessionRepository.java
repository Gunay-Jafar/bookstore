package com.developia.bookstore.repository;

import com.developia.bookstore.model.Session;
import com.developia.bookstore.model.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByStatus(SessionStatus status);
}
