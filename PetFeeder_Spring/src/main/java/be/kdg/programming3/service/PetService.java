package be.kdg.programming3.service;


import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.Pet;

import java.util.List;

public interface PetService {
   List<Pet> findAll();
   Pet save(Pet pet);
   //Pet save(String name, int age, String gender, Breed animalType, double weight);
   Pet findById(Long id);
   void deleteById(Long id);

}
