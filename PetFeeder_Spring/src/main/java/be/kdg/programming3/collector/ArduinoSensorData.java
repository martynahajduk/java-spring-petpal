package be.kdg.programming3.collector;

import be.kdg.programming3.domain.Feeder;


public class ArduinoSensorData extends PetPalData {

    // Attributes
    private Double reservoirHeight;
    private Double bowlWeight;
    private Double petWeight;
    private Feeder feeder;

    // Constructor
    public ArduinoSensorData(Double reservoirHeight, Double bowlWeight, Double petWeight,Feeder feeder) {
        super(feeder.getId());
        this.reservoirHeight = reservoirHeight;
        this.bowlWeight = bowlWeight;
        this.petWeight = petWeight;
        this.feeder = feeder;
         }

    // Getters and Setters
    public Double getBowlWeight() {
        return bowlWeight;
    }

    public void setBowlWeight(Double bowlWeight) {
        this.bowlWeight = bowlWeight;
    }

    public Double getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(Double petWeight) {
        this.petWeight = petWeight;
    }

    public Double getReservoirHeight() {
        return reservoirHeight;
    }

    public void setReservoirHeight(Double reservoirHeight) {
        this.reservoirHeight = reservoirHeight;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
    }

    // Process data method
    @Override
    public void processData() {
        System.out.println("Processing Arduino Sensor Data with ID: " + getId());
    }
}
