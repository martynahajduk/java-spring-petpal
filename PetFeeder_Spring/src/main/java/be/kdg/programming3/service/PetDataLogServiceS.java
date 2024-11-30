package be.kdg.programming3.service;


import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.repository.PetDataLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PetDataLogServiceS implements PetDataLogService {
    private final PetDataLogRepository petDataLogRepository;

    @Autowired
    public PetDataLogServiceS(PetDataLogRepository petDataLogRepository) {
        this.petDataLogRepository = petDataLogRepository;
    }

    public PetDataLog getPetDataLogById(Long id) {
        return petDataLogRepository.findById(id).orElse(null);
    }

    public List<PetDataLog> findAll() {
        return petDataLogRepository.findAll();
    }

    public PetDataLog save(PetDataLog petDataLog) {
        return petDataLogRepository.save(petDataLog);
    }

    public void deleteById(Long id) {
        petDataLogRepository.deleteById(id);
    }

    public List<PetDataLog> getAllLogs() {
        return petDataLogRepository.findAll();
    }

    public Double getFoodLevelPercentage(Long petDataLogId) {
        PetDataLog petDataLog = new PetDataLog();
        Double percentage =  (petDataLog.getReservoirHeight()) * 100;
        return (double) Math.round(percentage);
    }
}
