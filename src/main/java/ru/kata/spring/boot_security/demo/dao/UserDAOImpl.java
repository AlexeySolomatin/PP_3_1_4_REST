package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;

    public UserDAOImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    public User getUserByEmail(String email) {
        return (User) entityManager.createQuery("FROM User user JOIN FETCH user.roles WHERE user.email = :email")
                .setParameter("email", email).getSingleResult();
    }


//    public String getPasswordById(int id) {
//        return (String) entityManager.createNativeQuery("SELECT password FROM user WHERE user.id = :id")
//                .setParameter("id", id).getSingleResult();
//    }

    public void save(User user) {
        entityManager.persist(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() != null) {
            user.setRoles(listRoles(user));
        }
    }

    public void update(User user) {

        int id = entityManager.merge(user).getId();
//        System.out.println(getPasswordById(id));
//        if (user.getPassword().length() > 0) {
//            getUser(id).setPassword(passwordEncoder.encode(user.getPassword()));
//        }
        if (user.getRole() != null) {
            getUser(id).setRoles(listRoles(user));
        }
    }

    private List<Role> listRoles(User user) {
        List<Role> roles = new ArrayList<>();
        if (user.getRole().toLowerCase().contains("admin")) {
            roles.add(new Role(1, "ROLE_ADMIN"));
        }
        if (user.getRole().toLowerCase().contains("user")) {
            roles.add(new Role(2, "ROLE_USER"));
        }
        return roles;
    }

    public void delete(int id) {
        entityManager.remove(getUser(id));
    }
}