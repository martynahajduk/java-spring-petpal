package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.service.FeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feeders")
public class FeederController {

    private final FeederService feederService;

    @Autowired
    public FeederController(FeederService feederService) {
        this.feederService = feederService;
    }

    @GetMapping
    public List<Feeder> getAllFeeders() {
        return feederService.findAll();
    }



    /*@PostMapping
    public Feeder createFeeder(@RequestBody Feeder feeder) {
        return feederService.save(feeder);
    }*/
    @PostMapping("/add")
    public ResponseEntity<Feeder> createFeeder(@RequestBody Feeder feeder) {
        Feeder createdFeeder = feederService.save(feeder);
        return ResponseEntity.ok(createdFeeder);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeeder(@PathVariable Long id) {
        feederService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
