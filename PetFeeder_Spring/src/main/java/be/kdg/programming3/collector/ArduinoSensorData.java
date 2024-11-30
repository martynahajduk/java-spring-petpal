package be.kdg.programming3.collector;

public class ArduinoSensorData extends PetPalData {

    // Attributes
    private Double reservoirHeight;
    private Double bowlWeight;
    private Double petWeight;
    private Double sensorId; // Add id as a field

    // Constructor
    public ArduinoSensorData(Double reservoirHeight, Double bowlWeight, Double petWeight, Double id) {
        this.reservoirHeight = reservoirHeight;
        this.bowlWeight = bowlWeight;
        this.petWeight = petWeight;
        this.sensorId = sensorId; // Assign to sensorId
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

    public Double getSensorId() {
        return sensorId;
    }

    public void setSensorId(Double sensorId) {
        this.sensorId = sensorId;
    }

    // Process data method
    @Override
    public void processData() {
        System.out.println("Processing Arduino Sensor Data with ID: " + getId());
    }
}
