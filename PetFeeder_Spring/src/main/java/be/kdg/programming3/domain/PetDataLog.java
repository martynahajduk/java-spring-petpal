package be.kdg.programming3.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PetDataLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary Key for PetDataLog

    // Many PetDataLogs can be associated with one Feeder
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "feeder_id")
    @JsonIgnore
    // Foreign key to Feeder
    private Feeder feeder;

    // Many PetDataLogs can be associated with one Pet
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "pet_id") // Foreign key to Pet
//    private Pet pet;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "animal_type", nullable = true)
//    private Breed animalType;


//    private int age;
    @Column(name="age_weeks")
    private int ageWeeks;
    @Column(name="breed")
    @Enumerated(EnumType.STRING)
    private Breed breed;
    @JoinColumn(name="sex")
    private String sex;
    private Double petWeight;
    private Double bowlWeight;
    private Double reservoirHeight;
   @JsonIgnore
    private LocalDateTime timestamp; // Timestamp of the feeding


    public PetDataLog() {
    }

    public PetDataLog(Feeder feeder,/* Pet pet, int age, Breed animalType,*/ Double reservoirHeight, Double petWeight, Double bowlWeight, LocalDateTime timestamp) {
        this.feeder = feeder;
//        this.pet = pet;
//        this.animalType = animalType;
//        this.age = age;
        this.reservoirHeight = reservoirHeight;
        this.petWeight = petWeight;
        this.bowlWeight = bowlWeight;
        this.timestamp = timestamp;


    }

    public PetDataLog(Long id,/* int age, Breed animalType,*/ Double petWeight, Double bowlWeight, Double reservoirHeight) {
        this.id = id;
//        this.age = age;
//        this.animalType = animalType;
        this.petWeight = petWeight;
        this.bowlWeight = bowlWeight;
        this.reservoirHeight = reservoirHeight;
    }




    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public Breed getAnimalType() {
//        return animalType;
//    }
//
//    public void setAnimalType(Breed animalType) {
//        this.animalType = animalType;
//    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
    }



//    public Pet getPet() {
//        return pet;
//    }
//
//    public void setPet(Pet pet) {
//        this.pet = pet;
//    }

    public Double getReservoirHeight() {
        return reservoirHeight;
    }

    public void setReservoirHeight(Double reservoirHeight) {
        this.reservoirHeight = reservoirHeight;
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

    public int getAgeWeeks() {
        return ageWeeks;
    }

    public void setAgeWeeks(int ageWeeks) {
        this.ageWeeks = ageWeeks;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "PetDataLog{" +
//
                ", id=" + id +
                ", feeder=" + feeder +
//                ", pet=" + pet +
//                ", animalType=" + animalType +
                ", petWeight=" + petWeight +
                ", bowlWeight=" + bowlWeight +
                ", reservoirHeight=" + reservoirHeight +
                ", timestamp=" + timestamp +
                '}';
    }
}
