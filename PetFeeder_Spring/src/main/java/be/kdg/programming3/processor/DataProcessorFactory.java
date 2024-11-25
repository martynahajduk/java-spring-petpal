package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.service.PetDataLogService;
import org.springframework.stereotype.Component;

@Component
public class DataProcessorFactory {

    private final PetDataLogService petDataLogService;

    public DataProcessorFactory(PetDataLogService petDataLogService) {
        this.petDataLogService = petDataLogService;
    }

    public DataProcessor getProcessor(PetPalData data) {
        CompositeProcessor compositeProcessor = new CompositeProcessor();

        // Add FeederDataProcessor if data is ArduinoSensorData
        if (data instanceof ArduinoSensorData) {
            compositeProcessor.addProcessor(new FeederDataProcessor(petDataLogService));
        }

        if (compositeProcessor.hasProcessors()) {
            return compositeProcessor;
        }

        throw new IllegalArgumentException("No processor found for data type: " + data.getClass().getSimpleName());
    }
}
