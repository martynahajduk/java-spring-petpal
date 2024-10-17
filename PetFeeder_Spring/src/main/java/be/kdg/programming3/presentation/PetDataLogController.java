package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.PetDataLogService;
import be.kdg.programming3.service.PetDataLogServiceS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/data-logs")
public class PetDataLogController {
    private final PetDataLogService petDataLogService;

    @Autowired
    public PetDataLogController(PetDataLogService petDataLogService) {
        this.petDataLogService = petDataLogService;
    }

    @GetMapping
    public List<PetDataLog> getAllPetDataLog() {
        return petDataLogService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PetDataLog> getPetDataLogById(@PathVariable Long id) {
        PetDataLog petDataLog = petDataLogService.findPetDataLogById(id);
        return petDataLog != null ? ResponseEntity.ok(petDataLog) : ResponseEntity.notFound().build();
    }

    /*@PostMapping
    public PetDataLog createPetDataLog(@RequestBody PetDataLog petDataLog) {
        //PetDataLog createdPetDataLog = petDataLogService.save(petDataLog);
        //return ResponseEntity.status(201).body(createdPetDataLog);
        return petDataLogService.save(petDataLog);
    }*/
    @PostMapping("/add")
    public ResponseEntity<PetDataLog> createPetDataLog(@RequestBody PetDataLog petDataLog) {
        PetDataLog createdPetDataLog = petDataLogService.save(petDataLog);
        return ResponseEntity.ok(createdPetDataLog);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetDataLog(@PathVariable Long id) {
        petDataLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
