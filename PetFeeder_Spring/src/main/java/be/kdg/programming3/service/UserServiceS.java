package be.kdg.programming3.service;

import be.kdg.programming3.domain.User;

import be.kdg.programming3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceS implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceS(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    /*public User save(String name, String email, String password) {
        return userRepository.save(new User(name,email,password));
    }*/

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
