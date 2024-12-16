package be.kdg.programming3.service;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.repository.FeederRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeederServiceImpl implements FeederService {

    private final FeederRepository feederRepository;

    @Autowired
    public FeederServiceImpl(FeederRepository feederRepository) {
        this.feederRepository = feederRepository;
    }

    public Feeder findById(Long id) {
        return feederRepository.findById(id).orElse(null);
    }

    @Override
    public Feeder findOrCreateById(Long id) {
        return feederRepository.findById(id).orElse(save(new Feeder(id)));
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

