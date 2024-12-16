package be.kdg.programming3.service;


import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;

import java.util.List;
import java.util.Set;

public interface PetService {
   List<Pet> findAll();
   Pet save(Pet pet);
   //Pet save(String name, int age, String gender, Breed animalType, double weight);
   Pet findById(Long id);
   void deleteById(Long id);
   //List<Feeder> findAllFeedersById(Long id);
//   User findRandUserByFeederId(Long id);

    User findRandUserByPetId(Long id);

   Integer findPetAgeWeeks(Pet pet);
    Set<Pet> findPetsByUserId(Long userId);



}
