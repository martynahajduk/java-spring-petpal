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
    private String petWeight;
    @Column(name="food_ate", nullable = false)
    private String foodAte;
    @Enumerated(EnumType.STRING)
    @Column(name="animal_type", nullable = false)
    private Breed animalType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="pet_id", nullable = false)
    private Pet pet;

    protected PetDataLog(){}

    public PetDataLog(Long id, String petWeight, String foodAte, Breed animalType, Pet pet) {
        this.id = id;
        this.petWeight = petWeight;
        this.foodAte = foodAte;
        this.animalType = animalType;
        this.pet = pet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public String getFoodAte() {
        return foodAte;
    }

    public void setFoodAte(String foodAte) {
        this.foodAte = foodAte;
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

    @Override
    public String toString() {
        return "PetDataLogService{" +
                "id=" + id +
                ", petWeight='" + petWeight + '\'' +
                ", foodAte='" + foodAte + '\'' +
                ", animalType=" + animalType +
                ", pet=" + pet +
                '}';
    }
}
