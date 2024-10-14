package be.kdg.programming3.service;


import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.repository.PetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceS implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceS(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }


}
