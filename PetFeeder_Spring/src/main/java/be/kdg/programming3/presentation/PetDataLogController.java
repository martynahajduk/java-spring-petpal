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

//@Controller
//@RequestMapping("/data-logs")
public class PetDataLogController {
 //  private final PetDataLogService petDataLogService;

 //  @Autowired
 //  public PetDataLogController(PetDataLogService petDataLogService) {
 //      this.petDataLogService = petDataLogService;
 //  }

 //  @GetMapping
 //  public List<PetDataLog> getAllPetDataLog() {
 //      return petDataLogService.findAll();
 //  }


 //  @GetMapping("/{id}")
 //  public PetDataLog getPetDataLogById(@PathVariable Long id) {
 //      return petDataLogService.getPetDataLogById(id);
 //  }

 //  @PostMapping("add")
 //  public PetDataLog createPetDataLog(@RequestBody PetDataLog petDataLog) {
 //      return petDataLogService.save(petDataLog);
 //  }

 //  @DeleteMapping("/{id}")
 //  public void deletePetDataLog(@PathVariable Long id) {
 //      petDataLogService.deleteById(id);
 //  }
}
