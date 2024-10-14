package be.kdg.programming3.service;


import be.kdg.programming3.domain.Pet;

import java.util.List;

public interface PetService {
   List<Pet> findAll();
    Pet save(Pet pet);
    void deleteById(Long id);

}
