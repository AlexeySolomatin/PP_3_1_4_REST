package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserByEmail(String email);

    void save(User user);
    void update(User updateUser);
    void delete(int id);

    User getUser(int id);
}
