package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class AppController {
    private final UserService userService;
    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user")
    public String user() {
        return "admin";
    }

    @GetMapping("/login")
    public RedirectView redirect() {
        return new RedirectView("/");
    }
}