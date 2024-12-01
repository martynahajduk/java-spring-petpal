package be.kdg.programming3.service;


import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.FeederRepository;
import be.kdg.programming3.repository.PetRepository;

import be.kdg.programming3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PetServiceS implements PetService {


    private final PetRepository petRepository;
    private final FeederRepository feederRepository;
    private final UserRepository userRepository;

    @Autowired
    public PetServiceS(PetRepository petRepository, FeederRepository feederRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.feederRepository = feederRepository;
        this.userRepository = userRepository;
    }

    public Integer getPetAgeWeeks(Pet pet) {
        return pet.calculateAgeWeeks();
    }


    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
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

    @Override
    public User findRandUserByFeederId(Long id) {
        return userRepository.findAll().stream().
                filter(u -> u.getFeeder().getId().equals(id))
                .findFirst().get();
    }

    @Override
    public Feeder getFeederByUserId(Long userId) {
        Feeder feederById;
        if (userRepository.existsById(userId)) {
            if(feederRepository.existsById((userRepository.findById(userId).get().getFeeder().getId()))) {
               feederById  = feederRepository.findById((userRepository.findById(userId).get().getFeeder().getId())).get();
            }else {
                throw new NoSuchElementException();
            }
        }else {
            throw new NoSuchElementException();
        }
        return feederById;
    }

}
