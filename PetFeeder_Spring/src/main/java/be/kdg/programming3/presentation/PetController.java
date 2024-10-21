package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.PetService;
import be.kdg.programming3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
    public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Pet pet = petService.findById(id);
        return pet != null ? ResponseEntity.ok(pet) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petService.save(pet);
    }

  // @PutMapping("/{id}")
  // public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet petDetails) {
  //     Pet updatedPet = petService.updatePet(id, petDetails);
  //     return updatedPet != null ? ResponseEntity.ok(updatedPet) : ResponseEntity.notFound().build();
  // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
