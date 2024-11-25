package be.kdg.programming3.service;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findUserById(Long id);
    User save(User user);
    void deleteById(Long id);
    User loginUser(String email, String password);
    User registerUser(String name, String email, String password, Feeder feeder);
    }