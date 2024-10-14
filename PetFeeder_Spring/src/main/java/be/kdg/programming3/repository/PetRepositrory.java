package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;

import java.util.List;

public interface PetRepositrory {
    List<Pet> getPets();

    List<User> getUsers();

    List<Feeder> getFeeders();

    Pet createPets(Pet pet);

    User createUsers(User user);

    Feeder creatingFeeder(Feeder feeder);
}
