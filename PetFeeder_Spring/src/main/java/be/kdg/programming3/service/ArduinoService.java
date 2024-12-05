package be.kdg.programming3.service;

public interface ArduinoService {
    void processSensorData(Double reservoirHeight, Double bowlWeight, Double petWeight, Long feederId);
}
