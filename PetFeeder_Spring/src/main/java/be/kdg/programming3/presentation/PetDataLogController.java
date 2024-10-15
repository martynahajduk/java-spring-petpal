package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.PetDataLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class PetDataLogController {
    private final PetDataLogService petDataLogService;

    @Autowired
    public PetDataLogController(PetDataLogService petDataLogService) {
        this.petDataLogService = petDataLogService;
    }

    @GetMapping
    public ResponseEntity<List<PetDataLog>> getAllPetDataLogs() {
        List<PetDataLog> petDataLogs = petDataLogService.findAll();
        return ResponseEntity.ok(petDataLogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDataLog> getPetDataLogById(@PathVariable Long id) {
        PetDataLog petDataLog = petDataLogService.findPetDataLogById(id);
        return petDataLog != null ? ResponseEntity.ok(petDataLog) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public PetDataLog createPetDataLog(@RequestBody PetDataLog petDataLog) {
        //PetDataLog createdPetDataLog = petDataLogService.save(petDataLog);
        //return ResponseEntity.status(201).body(createdPetDataLog);
        return petDataLogService.save(petDataLog);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetDataLog(@PathVariable Long id) {
        petDataLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
