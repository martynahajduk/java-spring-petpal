package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.service.FeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

  // @GetMapping("/{id}")
  // public ResponseEntity<Feeder> getFeederById(@PathVariable Long id) {
  //     Feeder feeder = feederService.findById(id);
  //     return feeder != null ? ResponseEntity.ok(feeder) : ResponseEntity.notFound().build();
  // }

    @PostMapping
    public Feeder createFeeder(@RequestBody Feeder feeder) {
        return feederService.save(feeder);
    }

//  @PutMapping("/{id}")
//  public ResponseEntity<Feeder> updateFeeder(@PathVariable Long id, @RequestBody Feeder feederDetails) {
//      Feeder updatedFeeder = feederService.updateFeeder(id, feederDetails);
//      return updatedFeeder != null ? ResponseEntity.ok(updatedFeeder) : ResponseEntity.notFound().build();
//  }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeeder(@PathVariable Long id) {
        feederService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
