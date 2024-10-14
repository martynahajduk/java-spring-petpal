package be.kdg.programming3.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int age;
    private String gender;
    private Breed animalType;
    private double weight;


    protected Pet() {
    } //Encapsulation: You don’t want other parts of the application
    // (except for subclasses) to use the default constructor. By marking it as
    // protected, only the JPA framework
    // (and subclasses of Pet) can use it



    public Pet(Long id, String name, int age, Breed animalType, String gender, double weight) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", animalType=" + animalType +
                ", gender='" + gender + '\'' +
                ", weight=" + weight +
                '}';
    }
}



