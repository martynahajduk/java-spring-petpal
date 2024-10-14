package be.kdg.programming3.service;

import be.kdg.programming3.domain.Users;
import be.kdg.programming3.repository.PetRepositrory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private PetRepositrory petRepositrory;


    public UserServiceImpl(PetRepositrory petRepositrory) {
        logger.info("Creating PetRepository");
        this.petRepositrory = petRepositrory;
    }

    @Override
    public List<Users> getUser() {
        logger.info("Getting users");
        return petRepositrory.getUsers();
    }

    @Override
    public Users addUser(String name, String email, String password){
        logger.info("Adding user {}", name);
        return petRepositrory.createUsers(new Users(name, email, password));
    }


}
