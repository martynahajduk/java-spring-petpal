package be.kdg.programming3.service;


import be.kdg.programming3.domain.Pet;

import java.util.List;

public interface PetService {
   List<Pet> findAll();
    Pet save(Pet pet);
    Pet findById(Long id);
    void deleteById(Long id);

}
