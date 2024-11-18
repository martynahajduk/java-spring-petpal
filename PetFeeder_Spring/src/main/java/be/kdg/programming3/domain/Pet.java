package be.kdg.programming3.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "age", nullable = false)
    private int age;
    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type", nullable = false)
    private Breed animalType;
    @Column(name = "weight")
    private double petWeight;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false) // Allows null for unassigned pets
    private User user;

    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
    private Set<PetDataLog> petDataLogs;

    //@OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<PetDataLog> petDataLogs = new ArrayList<>();


    public Pet() {
    }

    //for arduino
    public Pet(double petWeight) {
        this.petWeight = petWeight;
        System.out.println(1);
    }

    public Pet(String name, int age, Breed animalType,  double petWeight) {
        this.age = age;
        this.name = name;
        this.animalType = animalType;
        this.petWeight = petWeight;
        System.out.println(2);
    }

    public Pet(String name, Breed animalType, double petWeight, User user) {
        this.name = name;
        this.animalType = animalType;
        this.petWeight = petWeight;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Breed getAnimalType() {
        return animalType;
    }

    public void setAnimalType(Breed animalType) {
        this.animalType = animalType;
    }
    public Set<PetDataLog> getPetDataLogs() {
        return petDataLogs;
    }

    public void setPetDataLogs(Set<PetDataLog> petDataLogs) {
        this.petDataLogs = petDataLogs;
    }

    public double getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(double petWeight) {
        this.petWeight = petWeight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

