package be.kdg.programming3.service;


import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.repository.FeederRepository;
import be.kdg.programming3.repository.PetRepository;

import be.kdg.programming3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PetServiceImpl implements PetService {


    private final PetRepository petRepository;
    private final FeederRepository feederRepository;
    private final UserRepository userRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, FeederRepository feederRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.feederRepository = feederRepository;
        this.userRepository = userRepository;
    }

    public Integer findPetAgeWeeks(Pet pet) {
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
    public Pet findByFeederId(Long id) {
        return petRepository.findByFeederId(id);
    }

    @Override
    public User findRandUserByPetId(Long id) {
        return userRepository.findAll().stream().
                filter(u -> !u.getPets().stream().filter(p -> p.getId().equals(id)).toList().isEmpty())
                .findFirst().get();
    }

    @Override
    public Set<Pet> findPetsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null); // Use optional to handle null
        if (user == null || user.getPets() == null) {
            return null; // Return null if no user or pet exists
        }
        return user.getPets();
    }

}
