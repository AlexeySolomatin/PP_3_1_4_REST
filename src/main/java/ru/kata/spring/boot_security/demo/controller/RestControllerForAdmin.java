package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class RestControllerForAdmin {
    private final UserService userService;
    public RestControllerForAdmin(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public User getAdmin(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User save(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id){
        userService.delete(id);
        return id;
    }
}