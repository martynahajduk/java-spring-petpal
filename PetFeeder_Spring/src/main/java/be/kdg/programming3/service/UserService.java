package be.kdg.programming3.service;

import be.kdg.programming3.domain.User;

import java.util.List;

public interface UserService {


    List<User> getUser();
    User addUser(Long id,String name, String email, String password);
}