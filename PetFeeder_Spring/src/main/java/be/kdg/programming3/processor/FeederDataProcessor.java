package be.kdg.programming3.processor;

import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.PetDataLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//concrete processor for processing the ArduinoSensorData
//saves the data of sensors in database through FeederService

public class FeederDataProcessor extends DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FeederDataProcessor.class);
    private final PetDataLogService petDataLogService;

    public FeederDataProcessor(PetDataLogService petDataLogService) {
        this.petDataLogService = petDataLogService;
    }

    @Override
    public void saveToDatabase(PetPalData data) {
        if (data instanceof ArduinoSensorData) {
            ArduinoSensorData sensorData = (ArduinoSensorData) data;

            // Log the sensor data
            logger.info("Processing Sensor Data for Feeder ID: {}", sensorData.getId());
            logger.info("Reservoir Height: {}", sensorData.getReservoirHeight());
            logger.info("Bowl Weight: {}", sensorData.getBowlWeight());
            logger.info("Pet Weight: {}", sensorData.getPetWeight());

            // Ensure all required attributes are present
            if (sensorData.getReservoirHeight() != null &&
                    sensorData.getBowlWeight() != null &&
                    sensorData.getPetWeight() != null) {

                // Create a new PetDataLog with all required attributes
                PetDataLog petDataLog = new PetDataLog();
                petDataLog.setReservoirHeight(sensorData.getReservoirHeight());
                petDataLog.setBowlWeight(sensorData.getBowlWeight());
                petDataLog.setPetWeight(sensorData.getPetWeight());

                // Save to database
                petDataLogService.save(petDataLog);
                logger.info("PetDataLog successfully saved for Feeder ID: {}", sensorData.getId());
            } else {
                logger.warn("Incomplete sensor data for Feeder ID: {}. Skipping save.", sensorData.getId());
            }
        } else {
            throw new IllegalArgumentException("Unsupported data type for FeederDataProcessor");
        }
    }
}

