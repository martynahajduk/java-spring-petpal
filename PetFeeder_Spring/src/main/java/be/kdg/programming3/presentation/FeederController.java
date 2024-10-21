package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.service.FeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//@RequestMapping("/feeders")

public class FeederController {


//   private final FeederService feederService;

//   @Autowired
//   public FeederController(FeederService feederService) {
//       this.feederService = feederService;
//   }

//   @GetMapping
//   public List<Feeder> getAllFeeders() {
//       return feederService.findAll();
//   }

//   @GetMapping("/{id}")
//   public Feeder getFeederById(@PathVariable Long id) {
//       return feederService.findById(id);
//   }
//   @PostMapping("/add")
//   public Feeder createFeeder(@RequestBody Feeder feeder) {
//       return feederService.save(feeder);
//   }

//
//   @DeleteMapping("/{id}")
//   public void deleteFeeder(@PathVariable Long id) {
//       feederService.deleteById(id);
//   }
//
}