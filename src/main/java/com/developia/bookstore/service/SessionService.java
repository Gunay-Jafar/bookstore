package com.developia.bookstore.service;

import com.developia.bookstore.model.Session;
import com.developia.bookstore.model.User;

public interface SessionService {
    Session findActiveSession();

    void create(User user);

    void delete();
}
