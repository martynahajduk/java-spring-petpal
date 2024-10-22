package be.kdg.programming3.domain;

import jakarta.persistence.*;
@Entity
@Table(name="pet_data_log")
public class PetDataLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="pet_weight_log", nullable = false)
    private double petWeight;
    @Column(name="portion", nullable = false)
    private double portion;
    @Enumerated(EnumType.STRING)
    @Column(name="animal_type", nullable = false)
    private Breed animalType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="pet_id", nullable = false)
    private Pet pet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feeder_id", nullable = false)
    private Feeder feeder;

    public PetDataLog(){}

    public PetDataLog(double petWeight, Breed animalType, double portion, Pet pet, Feeder feeder) {
        this.petWeight = petWeight;
        this.portion = portion;
        this.animalType = animalType;
        this.pet = pet;
        this.feeder=feeder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(double petWeight) {
        this.petWeight = petWeight;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public Breed getAnimalType() {
        return animalType;
    }

    public void setAnimalType(Breed animalType) {
        this.animalType = animalType;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
        if (feeder != null) {
            this.portion = feeder.getPortion();
        }
    }

    @Override
    public String toString() {
        return "PetDataLog{" +
                "id=" + id +
                ", petWeight=" + petWeight +
                ", portion=" + portion +
                ", animalType=" + animalType +
                ", pet=" + pet +
                ", feeder=" + feeder +
                '}';
    }
}
