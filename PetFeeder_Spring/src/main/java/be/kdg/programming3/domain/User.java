package be.kdg.programming3.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;


@Entity
@Table(name="app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "feeder_id")
    private Feeder feeder;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "pet_id",nullable = true)
    private Pet pet;



    public User(String name, String email, String password,  Feeder feeder,Pet pet) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.feeder = feeder;
        this.pet = pet;


        // Setting this user in the pet's users set
        if (pet != null) {
            if (pet.getUsers() == null) {
                pet.setUsers(new HashSet<>());
            }
            pet.getUsers().add(this);
        }
    }

    public User() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    }
}


