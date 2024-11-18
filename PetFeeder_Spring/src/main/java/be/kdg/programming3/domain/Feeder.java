package be.kdg.programming3.domain;

import jakarta.persistence.*;

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
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="reservoir_level", nullable = false)
    private double reservoirLevel;
    private double bowlWeight;
    private double petWeight;


    // One Feeder can have multiple PetDataLogs
    @OneToMany(mappedBy = "feeder", fetch = FetchType.EAGER)
    private Set<PetDataLog> petDataLogs;

    // One Feeder can have multiple Schedules
    @OneToMany(mappedBy = "feeder", fetch = FetchType.EAGER)
    private List<Schedule> schedule;



    public Feeder() {}

    public Feeder(double reservoirLevel , double bowlWeight,double petWeight,Long id) {
        this.reservoirLevel = reservoirLevel;
        this.bowlWeight = bowlWeight;
        this.petWeight = petWeight;
        this.id= id;

    }

    public double getBowlWeight() {
        return bowlWeight;
    }

    public void setBowlWeight(double bowlWeight) {
        this.bowlWeight = bowlWeight;
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

    public double getReservoirLevel() {
        return reservoirLevel;
    }

    public void setReservoirLevel(double reservoirLevel) {
        this.reservoirLevel = reservoirLevel;
    }

    public List<Schedule> getSchedules() {
        return schedule;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedule = schedules;
    }

    @Override
    public String toString() {
        return "Feeder{" +
                "id=" + id +
                ", reservoirLevel=" + reservoirLevel +
                "bowlWeight=" + bowlWeight/*+
                ", nextFeedingDate=" + nextFeedingDate +
                ", nextFeedingTime=" + nextFeedingTime */+
                '}';
    }
}
