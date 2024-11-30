package be.kdg.programming3.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SimulatedHeartbeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heartbeatRate;
    private Long petId;  // Added petId field

    public SimulatedHeartbeatEntity(int heartbeatRate, Long petId) {
        this.heartbeatRate = heartbeatRate;
        this.petId = petId;
    }

    public SimulatedHeartbeatEntity() {}

    public Long getId() {
        return id;
    }

    public int getHeartbeatRate() {
        return heartbeatRate;
    }

    public void setHeartbeatRate(int heartbeatRate) {
        this.heartbeatRate = heartbeatRate;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    @Override
    public String toString() {
        return "SimulatedHeartbeatEntity{" +
                "id=" + id +
                ", heartbeatRate=" + heartbeatRate +
                ", petId=" + petId +
                '}';
    }
}
