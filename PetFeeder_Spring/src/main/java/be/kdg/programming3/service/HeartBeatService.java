package be.kdg.programming3.service;

import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.domain.SimulatedHeartbeatEntity;

import java.util.List;

public interface HeartBeatService {
    List<SimulatedHeartbeatEntity> findAll();
    SimulatedHeartbeatEntity getHeartBeatByID(Long id);
    SimulatedHeartbeatEntity save(SimulatedHeartbeatEntity HeartBeat);
    void deleteById(Long id);
}
