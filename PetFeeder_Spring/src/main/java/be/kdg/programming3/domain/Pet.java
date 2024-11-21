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
    @OneToMany(mappedBy="pet",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
       private Set<User> users; //one pet can belong to multiple users

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

    public Pet(String name, int age, Breed animalType,  double petWeight, Set<User> users) {

        this.age = age;
        this.name = name;
        this.animalType = animalType;
        this.petWeight = petWeight;
        this.users = users;

        // Set this pet for each user in the set
        for (User user : users) {
            user.setPet(this);
        }

    }

    public Pet(String name, Breed animalType, double petWeight, User user) {
        this.name = name;
        this.animalType = animalType;
        this.petWeight = petWeight;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

