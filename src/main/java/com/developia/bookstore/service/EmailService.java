package com.developia.bookstore.service;

import com.developia.bookstore.model.OrderEmail;

public interface EmailService {
    void sendOrderEmail(OrderEmail orderEmail);
}
