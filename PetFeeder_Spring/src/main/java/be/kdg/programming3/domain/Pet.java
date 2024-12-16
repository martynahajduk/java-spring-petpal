package be.kdg.programming3.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
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

    @Column(name = "birthDate", nullable = true)
    private LocalDate birthDate;


    @Column(name = "animal_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Breed animalType;

    @Column(name = "weight")
    private double petWeight;

    @Column(name = "age_weeks", nullable = false)
    private int ageWeeks;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Feeder feeder;

    @Column(name="sex")
    private String sex;

//    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
//    private Set<PetDataLog> petDataLogs;

    //@OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<PetDataLog> petDataLogs = new ArrayList<>();


    public Pet() {
    }

    //for arduino
    public Pet(double petWeight) {
        this.petWeight = petWeight;
        System.out.println(1);
    }

    public Pet(String name, Feeder feeder, LocalDate birthDate, Breed animalType,  double petWeight, String sex, Set<User> users) {
        this.name = name;
        this.feeder = feeder;
        this.birthDate = birthDate;
        this.animalType = animalType;
        this.petWeight = petWeight;
        this.sex=sex;
        this.users = users;

    }

    public Pet(String name, Breed animalType, double petWeight, User user) {
        this.name = name;
        this.animalType = animalType;
        this.petWeight = petWeight;

    }
    public Integer getAgeWeeks() {
        return ageWeeks;
    }

    public void setAgeWeeks(int ageWeeks) {
        this.ageWeeks = ageWeeks;
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

//    public Set<PetDataLog> getPetDataLogs() {
//        return petDataLogs;
//    }
//
//    public void setPetDataLogs(Set<PetDataLog> petDataLogs) {
//        this.petDataLogs = petDataLogs;
//    }

    public int calculateAgeWeeks() {
        if (birthDate == null) {
            throw new IllegalStateException("birthDate is null");
        }

            Integer ageWeeks = (int) ChronoUnit.WEEKS.between(birthDate, LocalDate.now());
            setAgeWeeks(ageWeeks);
            return ageWeeks;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

