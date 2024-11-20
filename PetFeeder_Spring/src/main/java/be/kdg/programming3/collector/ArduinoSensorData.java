package be.kdg.programming3.collector;

public class ArduinoSensorData extends PetPalData{

    //this class is created to support any new data type parsed
    //contains all the attributes
    private Double reservoirHeight;
    private Double bowlWeight;
    private Double petWeight;
    private Double sensorValue;

    public ArduinoSensorData(Double reservoirHeight, Double bowlWeight, Double petWeight,Double sensorValue,Long id){
        this.reservoirHeight = reservoirHeight;
        this.bowlWeight = bowlWeight;
        this.petWeight = petWeight;
        this.sensorValue = sensorValue;
        this.setId(id);
    }


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

    public Double getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(Double sensorValue) {
        this.sensorValue = sensorValue;
    }
    @Override
    public void processData() {
        System.out.println("Processing Arduino Sensor Data with ID: " + getId());
    }
}
