package be.kdg.programming3.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PetDataLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary Key for PetDataLog

    // Many PetDataLogs can be associated with one Feeder
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "feeder_id") // Foreign key to Feeder
    private Feeder feeder;

    // Many PetDataLogs can be associated with one Pet
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id") // Foreign key to Pet
    private Pet pet;


    @Column(name="reservoir_level", nullable = false)
    private double reservoirLevel;
    private Double petWeight;
    private Double bowlWeight;
    private LocalDateTime timestamp; // Timestamp of the feeding

    public PetDataLog() {}

    public PetDataLog(Feeder feeder, Pet pet, double reservoirLevel, Double petWeight, Double bowlWeight, LocalDateTime timestamp) {
        this.feeder = feeder;
        this.pet = pet;
        this.reservoirLevel = reservoirLevel;
        this.petWeight = petWeight;
        this.bowlWeight = bowlWeight;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public double getReservoirLevel() {
        return reservoirLevel;
    }

    public void setReservoirLevel(double reservoirLevel) {
        this.reservoirLevel = reservoirLevel;
    }

    public Double getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(Double petWeight) {
        this.petWeight = petWeight;
    }

    public Double getBowlWeight() {
        return bowlWeight;
    }

    public void setBowlWeight(Double bowlWeight) {
        this.bowlWeight = bowlWeight;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PetDataLog{" +
                "id=" + id +
                ", feeder=" + feeder +
                ", pet=" + pet +
                ", reservoirLevel=" + reservoirLevel +
                ", petWeight=" + petWeight +
                ", bowlWeight=" + bowlWeight +
                ", timestamp=" + timestamp +
                '}';
    }
}


