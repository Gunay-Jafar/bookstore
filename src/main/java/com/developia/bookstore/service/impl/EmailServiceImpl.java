package com.developia.bookstore.service.impl;

import com.developia.bookstore.model.OrderEmail;
import com.developia.bookstore.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${sendgrid.apiKey}")
    private String apiKey;

    @Override
    public void sendOrderEmail(OrderEmail orderEmail) {

        logger.debug("send email: {}", orderEmail.getOrderNumber());

        Email from = new Email("example@gmail.com");
        String subject = "Order number: " + orderEmail.getOrderNumber();
        Content content = new Content("text/plain", orderEmail.getMessage());
        Personalization personalization = new Personalization();
        Email to = new Email();
        to.setName("Example Example");
        to.setEmail("example@gmail.com");
        personalization.addTo(to);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        mail.addPersonalization(personalization);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.GET);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
           logger.error("Error in sending email: {0}", ex);
        }
    }
}
