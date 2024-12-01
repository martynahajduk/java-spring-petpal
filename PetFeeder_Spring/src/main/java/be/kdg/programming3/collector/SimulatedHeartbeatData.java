package be.kdg.programming3.collector;

public class SimulatedHeartbeatData extends PetPalData {
    private final int heartbeatRate;
        private final Long petId;  // Added petId field

    public SimulatedHeartbeatData(int heartbeatRate, Long petId) {
        super(petId);
        this.heartbeatRate = heartbeatRate;
        this.petId = petId;
    }

    public int getHeartbeatRate() {
        return heartbeatRate;
    }

    public Long getPetId() {
        return petId;
    }

    @Override
    public String toString() {
        return "SimulatedHeartbeatData{heartbeatRate=" + heartbeatRate + ", petId=" + petId + '}';
    }

    @Override
    public void processData() {

    }
}
