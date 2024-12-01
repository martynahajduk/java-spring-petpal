package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.collector.SimulatedHeartbeatData;
import be.kdg.programming3.domain.SimulatedHeartbeatEntity;
import be.kdg.programming3.repository.SimulatedHeartbeatRepository;
import be.kdg.programming3.service.HeartBeatService;
import be.kdg.programming3.service.PetService;

public class HeartbeatDataProcessor extends DataProcessor {
    private final HeartBeatService HeartBeatService;

    public HeartbeatDataProcessor(be.kdg.programming3.service.HeartBeatService heartBeatService) {
        HeartBeatService = heartBeatService;
    }

    @Override
    public void saveToDatabase(PetPalData data, PetService petService) {
        if (data instanceof SimulatedHeartbeatData) {
            SimulatedHeartbeatData heartbeatData = (SimulatedHeartbeatData) data;

            // Save to database with petId
            SimulatedHeartbeatEntity entity = new SimulatedHeartbeatEntity(heartbeatData.getHeartbeatRate(), heartbeatData.getPetId());

            // Detect anomalies
            if (heartbeatData.getHeartbeatRate() < 60 || heartbeatData.getHeartbeatRate() > 100) {
                System.out.println("anomaly detected");
                HeartBeatService.save(entity);
            }

            System.out.println("Saved heartbeat data to database: " + entity);
        } else {
            throw new IllegalArgumentException("Invalid data type for HeartbeatDataProcessor");
        }
    }
}
