package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.PetDataLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class PDLController {

    private final PetDataLogService petDataLogService;

    public PDLController(PetDataLogService petDataLogService) {
        this.petDataLogService = petDataLogService;
    }

    @GetMapping("data-logs")
    public List<PetDataLog> getAllPetDataLog() {
        return petDataLogService.findAll();
    }

    @GetMapping("/data-logs/{id}")
    public PetDataLog getPetDataLogById(@PathVariable Long id) {
        return petDataLogService.getPetDataLogById(id);
    }

    @DeleteMapping("/data-logs/{id}")
    public void deletePetDataLog(@PathVariable Long id) {
        petDataLogService.deleteById(id);
    }

}

