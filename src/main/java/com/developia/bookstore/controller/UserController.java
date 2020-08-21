package com.developia.bookstore.controller;

import com.developia.bookstore.model.User;
import com.developia.bookstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.register(user);
        return "register";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password) {
        User user = userService.login(username, password);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/home";
    }
}
