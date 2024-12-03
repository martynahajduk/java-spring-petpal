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


    private final CompositeProcessor compositeProcessor;

    public DataProcessorFactory(CompositeProcessor compositeProcessor) {
        this.compositeProcessor = compositeProcessor;
    }


    public DataProcessor getProcessor(PetPalData data) {
         //check if it contains the specific processor
        if(compositeProcessor.hasProcessors()) {
            return compositeProcessor;

        }

        throw new IllegalArgumentException("No processor found for data type: " + data.getClass().getSimpleName());
    }
}
