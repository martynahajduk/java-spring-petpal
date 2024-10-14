package be.kdg.programming3.service;

import be.kdg.programming3.domain.Genders;
import be.kdg.programming3.domain.Pet;

import java.util.List;

public interface PetService {
    List<Pet> getPets();

    Pet addPet(String name, int age, Genders gender, String animalType, double weight);
}
