package be.kdg.programming3.service;


import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.repository.PetDataLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PetDataLogServiceImpl implements PetDataLogService {
    private final PetDataLogRepository petDataLogRepository;
    //private final PetDataLogService petDataLogService;
//    private final PetDataLogService petDataLogService;

    @Autowired
    public PetDataLogServiceImpl(PetDataLogRepository petDataLogRepository /*@Qualifier("petDataLogService") PetDataLogService petDataLogService*/) {
        this.petDataLogRepository = petDataLogRepository;

    }
    @Override
    public PetDataLog getPetDataLogById(Long id) {
        return petDataLogRepository.findById(id).orElse(null);
    }
    @Override
    public List<PetDataLog> findAll() {
        return petDataLogRepository.findAll();
    }
    @Override
    public PetDataLog save(PetDataLog petDataLog) {
        return petDataLogRepository.save(petDataLog);
    }
    @Override
    public void deleteById(Long id) {
        petDataLogRepository.deleteById(id);
    }

    @Override
    public List<PetDataLog> getAllLogs() {
        return petDataLogRepository.findAll();
    }

    @Override
    public Double getFoodLevelPercentageById(Long feederId) {
        PetDataLog latestLog = petDataLogRepository.findLatestByFeederId(feederId);
        double percentage = latestLog != null ? (latestLog.getReservoirHeight()) * 100 : 0;
        return (double) Math.round(percentage);
    }

    public List<PetDataLog> findAllByFeederId(Long feederId) {
        return petDataLogRepository.findAllByFeederId(feederId);
    }

    @Override
    public String getRealDataAsJson() {
        try{
            List<PetDataLog> realData = petDataLogRepository.findAll();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(realData);

        }catch (Exception e) {
            throw new RuntimeException("Failed to serialize real data: " + e.getMessage());
        }
    }


    }

