package com.developia.bookstore.service;

import com.developia.bookstore.model.User;

public interface UserService {
    void register(User user);

    User login(String username, String password);

    void logout();
}
