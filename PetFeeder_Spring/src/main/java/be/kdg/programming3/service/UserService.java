package be.kdg.programming3.service;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAll();
    User findUserById(Long id);
    User save(User user);
    void deleteById(Long id);
    User loginUser(String email, String password);
    User findUserByEmail(String email);
    Set<User> findUsersByPet(Pet pet);
//    List<User> findUsersByFeederId(Long feederId);
}