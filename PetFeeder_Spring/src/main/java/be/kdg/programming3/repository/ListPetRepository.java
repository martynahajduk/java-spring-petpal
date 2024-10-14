package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ListPetRepository implements PetRepositrory {
    private Logger logger = LoggerFactory.getLogger(ListPetRepository.class);
    private static List<Pet> pets = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List <Feeder> feeders = new ArrayList<>();

    @Override
    public List<Pet> getPets() {
        logger.info("getting pets");
        return pets;
    }

    @Override
    public List<User> getUsers() {
        logger.info("getting users");
        return users;
    }

    @Override
    public List<Feeder> getFeeders() {
        logger.info("getting feeders");
        return feeders;
    }

    @Override
    public Pet createPets(Pet pet){
        logger.info("creating pets {}", pets);
        pets.add(pet);
        return pet;
    }

    @Override
    public User createUsers(User user){
        logger.info("creating users {}", users);
        users.add(user);
        return user;
    }

    @Override
    public Feeder creatingFeeder(Feeder feeder){
        logger.info("creating feeder {}", feeders);
        feeders.add(feeder);
        return feeder;
    }
}
