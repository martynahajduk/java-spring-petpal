package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.Users;
import org.apache.catalina.User;

import java.util.List;

public interface PetRepositrory {
    List<Pet> getPets();

    List<Users> getUsers();

    List<Feeder> getFeeders();

    Pet createPets(Pet pet);

    Users createUsers(Users user);

    Feeder creatingFeeder(Feeder feeder);
}
