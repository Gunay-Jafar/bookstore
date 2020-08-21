package com.developia.bookstore.config;

import com.developia.bookstore.model.Session;
import com.developia.bookstore.model.enums.Role;
import com.developia.bookstore.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class RequestFilter implements Filter {

    private final SessionService sessionService;

    public RequestFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        log.trace("doFilter started");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        Session session = sessionService.findActiveSession();
        if (session == null)
            res.addHeader("role", Role.USER.name());
        else res.addHeader("role", session.getUser().getRole().name());

        filterChain.doFilter(req, res);

        log.trace("doFilter end");
    }
}
