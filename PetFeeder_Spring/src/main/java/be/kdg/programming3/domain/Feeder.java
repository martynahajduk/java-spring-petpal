package be.kdg.programming3.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="feeder")
public class Feeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = true)
    private Long id;

    // One Feeder can have multiple PetDataLogs
    @OneToMany(mappedBy = "feeder", fetch = FetchType.EAGER)

    private Set<PetDataLog> petDataLogs;

    @OneToOne
    private Pet pet;

    private String ip;



    public Feeder() {}

    public Feeder(Long id) {
        this.id= id;
    }

    public Feeder(Pet pet, Long id) {
        this.pet = pet;
        this.id = id;
    }

    public Feeder(String ip, Long id) {
        this.ip = ip;
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PetDataLog> getPetDataLogs() {
        return petDataLogs;
    }

    public void setPetDataLogs(Set<PetDataLog> petDataLogs) {
        this.petDataLogs = petDataLogs;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
