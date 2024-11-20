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

        CompositeProcessor compositeProcessor = new CompositeProcessor();

        if (data instanceof PetPalData) {
            //adding multiple processors to the composite
            compositeProcessor.addProcessor(new FeederDataProcessor(feederService));

        }
            if (compositeProcessor != null) {
                return compositeProcessor;
            }

        throw new IllegalArgumentException("No processor found for data type: " + data.getClass().getSimpleName());}
}