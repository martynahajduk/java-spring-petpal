package be.kdg.programming3.service;

import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.processor.DataProcessor;
import be.kdg.programming3.processor.DataProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArduinoServiceImpl implements ArduinoService {

    private final FeederService feederService;
    private final DataProcessorFactory dataProcessorFactory;
    private final PetService petService;
    private Feeder feeder;

    @Autowired
    public ArduinoServiceImpl(DataProcessorFactory dataProcessorFactory, FeederService feederService, PetService petService) {
        this.dataProcessorFactory = dataProcessorFactory;
        this.feederService = feederService;
        this.petService = petService;
    }


    @Override
    public void processSensorData(Double reservoirHeight, Double bowlWeight, Double petWeight,Long feederId) {
        //validating feederId and find Feeder
        if(feederId == null) {
            throw new IllegalArgumentException("Feeder id cannot be null");
        }
        Feeder feeder = feederService.findById(feederId);

        ArduinoSensorData sensorData = new ArduinoSensorData(reservoirHeight,bowlWeight,petWeight,feeder);

        //data processor factory to get the appropriate processor
        DataProcessor processor = dataProcessorFactory.getProcessor(sensorData);

        //process the data
        processor.saveToDatabase(sensorData,petService);

    }

}
