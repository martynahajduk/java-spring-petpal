package be.kdg.programming3.service;

import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.Pet;

import java.util.List;

public interface PetService {
    List<Pet> getPets();

    Pet addPet(Long id,String name, int age, Breed gender, String animalType, double weight);
}
