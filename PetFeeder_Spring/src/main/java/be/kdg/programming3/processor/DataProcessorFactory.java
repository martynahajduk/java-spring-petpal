package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.collector.SimulatedHeartbeatData;
import be.kdg.programming3.repository.SimulatedHeartbeatRepository;
import be.kdg.programming3.service.HeartBeatService;
import be.kdg.programming3.service.PetDataLogService;
import org.springframework.stereotype.Component;

@Component
public class DataProcessorFactory {

    private final PetDataLogService petDataLogService;
    private final HeartBeatService HeartBeatService;

    public DataProcessorFactory(PetDataLogService petDataLogService, HeartBeatService HeartBeatService) {
        this.petDataLogService = petDataLogService;
        this.HeartBeatService = HeartBeatService;
    }


    public DataProcessor getProcessor(PetPalData data) {
        CompositeProcessor compositeProcessor = new CompositeProcessor();

        // Add FeederDataProcessor if data is ArduinoSensorData
        if (data instanceof ArduinoSensorData) {
            compositeProcessor.addProcessor(new FeederDataProcessor(petDataLogService));
        }else if (data instanceof SimulatedHeartbeatData) {
            compositeProcessor.addProcessor(new HeartbeatDataProcessor(HeartBeatService));
        }

        if (compositeProcessor.hasProcessors()) {
            return compositeProcessor;
        }

        throw new IllegalArgumentException("No processor found for data type: " + data.getClass().getSimpleName());
    }
}
