package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.collector.SimulatedHeartbeatData;
import be.kdg.programming3.repository.SimulatedHeartbeatRepository;
import be.kdg.programming3.service.HeartBeatService;
import be.kdg.programming3.service.PetDataLogService;
import be.kdg.programming3.service.PetService;
import org.springframework.stereotype.Component;

@Component
public class DataProcessorFactory {

    private final PetDataLogService petDataLogService;
    private final HeartBeatService HeartBeatService;
    private final PetService petService;

    public DataProcessorFactory(PetDataLogService petDataLogService, HeartBeatService HeartBeatService, PetService petService) {
        this.petDataLogService = petDataLogService;
        this.HeartBeatService = HeartBeatService;
        this.petService = petService;
    }


    public DataProcessor getProcessor(PetPalData data) {
        CompositeProcessor compositeProcessor = new CompositeProcessor();

        // Add FeederDataProcessor if data is ArduinoSensorData
        if (data instanceof ArduinoSensorData) {
            compositeProcessor.addProcessor(new FeederDataProcessor(petDataLogService,petService));
        }else if (data instanceof SimulatedHeartbeatData) {
            compositeProcessor.addProcessor(new HeartbeatDataProcessor(HeartBeatService));
        }

        if (compositeProcessor.hasProcessors()) {
            return compositeProcessor;
        }

        throw new IllegalArgumentException("No processor found for data type: " + data.getClass().getSimpleName());
    }
}
