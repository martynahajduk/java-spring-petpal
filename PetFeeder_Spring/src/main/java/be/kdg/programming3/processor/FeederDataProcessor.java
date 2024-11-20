package be.kdg.programming3.processor;

import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.service.FeederService;

//concrete processor for processing the ArduinoSensorData
//saves the data of sensors in database through FeederService

public class FeederDataProcessor extends DataProcessor {
    private final FeederService feederService;

    public FeederDataProcessor(FeederService feederService) {
        this.feederService = feederService;
    }

    @Override
    public void saveToDatabase(PetPalData data) {
        if (data instanceof ArduinoSensorData) {
            ArduinoSensorData sensorData = (ArduinoSensorData) data;

            // handle the fields available in the sensor data
            if (sensorData.getReservoirHeight() != null &&
                    sensorData.getBowlWeight() != null &&
                    sensorData.getPetWeight() != null) {
                feederService.save(new Feeder(
                        sensorData.getReservoirHeight(),
                        sensorData.getBowlWeight(),
                        sensorData.getPetWeight(),
                        sensorData.getId()
                ));
            } else {
                System.out.println("Partial or alternative sensor data received: " + sensorData.getSensorValue());
            }
        } else {
            throw new IllegalArgumentException("Unsupported data type for FeederDataProcessor");
        }
    }
}
