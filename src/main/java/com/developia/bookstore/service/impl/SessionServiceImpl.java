package com.developia.bookstore.service.impl;

import com.developia.bookstore.model.Session;
import com.developia.bookstore.model.User;
import com.developia.bookstore.model.enums.SessionStatus;
import com.developia.bookstore.repository.SessionRepository;
import com.developia.bookstore.service.SessionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session findActiveSession() {
        return sessionRepository.findByStatus(SessionStatus.ACTIVE);
    }

    @Override
    public void create(User user) {
        Session session = Session
                .builder()
                .sessionId(UUID.randomUUID().toString())
                .user(user)
                .startTime(LocalDateTime.now())
                .status(SessionStatus.ACTIVE)
                .build();
        sessionRepository.save(session);
    }

    @Override
    public void delete() {
        Session session = findActiveSession();
        if (session == null) return;
        session.setEndTime(LocalDateTime.now());
        session.setStatus(SessionStatus.EXPIRED);
        sessionRepository.save(session);
    }
}
