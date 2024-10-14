package be.kdg.programming3.service;

import be.kdg.programming3.domain.Genders;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.repository.PetRepositrory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    private Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);
    private PetRepositrory petRepositrory;

    public PetServiceImpl(PetRepositrory petRepositrory) {
        logger.info("Creating PetRepository");
        this.petRepositrory = petRepositrory;
    }

    @Override
    public List<Pet> getPets() {
        logger.info("Getting pets");
        return petRepositrory.getPets();
    }

    @Override
    public Pet addPet(String name, int age, Genders gender, String animalType, double weight) {
        logger.info("Adding pet {}", name);
        return petRepositrory.createPets(new Pet(name, age, gender, animalType, weight));
    }


}
