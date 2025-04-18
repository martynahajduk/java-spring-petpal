package be.kdg.programming3.service;

import be.kdg.programming3.domain.PetDataLog;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PetDataLogService {

    List<PetDataLog> findAll();
    PetDataLog getPetDataLogById(Long id);
    PetDataLog save(PetDataLog petDataLog);
    void deleteById(Long id);
    List<PetDataLog> getAllLogs();

    //Calculate food left in the reservoir in Percentage
    Double getFoodLevelPercentageById(Long feederId);
    List<PetDataLog> findAllByFeederId(Long feederId);

    String getRealDataAsJson();
}
