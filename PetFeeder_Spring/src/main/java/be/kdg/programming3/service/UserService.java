package be.kdg.programming3.service;

import be.kdg.programming3.domain.Users;
import be.kdg.programming3.repository.PetRepositrory;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {


    List<Users> getUser();
    Users addUser(String name, String email, String password);
}