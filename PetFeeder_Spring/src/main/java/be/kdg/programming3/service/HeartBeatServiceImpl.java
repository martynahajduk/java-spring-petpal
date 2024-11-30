package be.kdg.programming3.service;

import be.kdg.programming3.domain.SimulatedHeartbeatEntity;
import be.kdg.programming3.repository.SimulatedHeartbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeartBeatServiceImpl implements HeartBeatService {
    private final SimulatedHeartbeatRepository heartbeatRepository;

    @Autowired
    public HeartBeatServiceImpl(SimulatedHeartbeatRepository simulatedHeartbeatRepository) {
        this.heartbeatRepository = simulatedHeartbeatRepository;
    }


    public List<SimulatedHeartbeatEntity> findAll() {
        return heartbeatRepository.findAll();
    }

    public SimulatedHeartbeatEntity getHeartBeatByID(Long id) {
        return heartbeatRepository.findById(id).orElse(null);
    }

    public SimulatedHeartbeatEntity save(SimulatedHeartbeatEntity HeartBeat) {
        return heartbeatRepository.save(HeartBeat);
    }

    public void deleteById(Long id) {
        heartbeatRepository.deleteById(id);

    }
}
