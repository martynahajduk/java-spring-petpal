package be.kdg.programming3.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="feeder")
public class Feeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="reservoir_level", nullable = false)
    private double reservoirLevel;
    @Column(name = "portion", nullable = false)
    private double portion;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;


    public Feeder() {}

    public Feeder(double reservoirLevel, double portion) {
        this.reservoirLevel = reservoirLevel;
        this.portion = portion;

    }
/*
    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getReservoirLevel() {
        return reservoirLevel;
    }

    public void setReservoirLevel(double reservoirLevel) {
        this.reservoirLevel = reservoirLevel;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }


    @Override
    public String toString() {
        return "Feeder{" +
                "id=" + id +
                ", reservoirLevel=" + reservoirLevel +
                ", portion=" + portion +
                ", schedule=" + schedule +
                '}';
    }
}
