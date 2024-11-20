package be.kdg.programming3.processor;

import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.service.FeederService;
import org.springframework.stereotype.Component;

//factory that returns the suitable processor for transmitted data
//identifies which processor to use , based on type of data

@Component
public class DataProcessorFactory {

    private final FeederService feederService;

    public DataProcessorFactory(FeederService feederService) {
        this.feederService = feederService;
    }

    public DataProcessor getProcessor(PetPalData data) {
        if (data instanceof ArduinoSensorData) {
            return new FeederDataProcessor(feederService);
        }
        throw new IllegalArgumentException("No processor found for data type: " + data.getClass().getSimpleName());
    }
}