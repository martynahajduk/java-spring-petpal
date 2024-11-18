package be.kdg.programming3.domain;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.Schedule;
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



    private Double petWeight;
    private Double bowlWeight;
    private LocalDateTime timestamp; // Timestamp of the feeding
    private String petName;
    public PetDataLog() {}

    public PetDataLog(Feeder feeder, Pet pet, Double petWeight, Double bowlWeight, LocalDateTime timestamp) {
        this.feeder = feeder;
        this.pet = pet;
        this.petWeight = petWeight;
        this.bowlWeight = bowlWeight;
        this.timestamp = timestamp;
    }

    // Getters and Setters
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

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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
                ", pet=" + pet +
                ", petName='" + petName + '\'' +
                ", petWeight=" + petWeight +
                ", feeder=" + feeder +
                ", bowlWeight=" + bowlWeight +
                '}';
    }
}
