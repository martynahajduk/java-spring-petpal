package be.kdg.programming3.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="age", nullable = false)
    private int age;
    @Column(name="gender", nullable = false)
    private String gender;
    @Enumerated(EnumType.STRING)
    @Column(name="animal_type", nullable = false)
    private Breed animalType;
    @Column(name="weight")
    private double weight;
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> owners;
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PetDataLog> petDataLogs = new ArrayList<>();


    public Pet(){}
    public Pet(double weight){
        this.weight = weight;
    }

    public Pet(String name, int age, Breed animalType, String gender, double weight) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.animalType = animalType;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Breed getAnimalType() {
        return animalType;
    }

    public void setAnimalType(Breed animalType) {
        this.animalType = animalType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<User> getOwners() {
        return owners;
    }

    public void setOwners(List<User> owners) {
        this.owners = owners;
    }
    public List<PetDataLog> getPetDataLogs() {
        return petDataLogs;
    }

    public void setPetDataLogs(List<PetDataLog> petDataLogs) {
        this.petDataLogs = petDataLogs;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", animalType=" + animalType +
                ", weight=" + weight +
                ", owners=" + owners +
                ", petDataLogs=" + petDataLogs +
                '}';
    }
}



