package be.kdg.programming3.service;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.repository.FeederRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeederServiceS implements FeederService {

    private final FeederRepository feederRepository;

    @Autowired
    public FeederServiceS(FeederRepository feederRepository) {
        this.feederRepository = feederRepository;
    }
    public List<Feeder> findAll() {
        return feederRepository.findAll();
    }
    public Feeder save(Feeder feeder) {
        return feederRepository.save(feeder);
    }
    public void deleteById(Long id) {
        feederRepository.deleteById(id);
    }

}

