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
    @OneToMany(mappedBy = "feeder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PetDataLog> petDataLogs = new ArrayList<>();

    /*@Column(name = "next_feeding_date")
    private LocalDate nextFeedingDate;

    @Column(name = "next_feeding_time")
    private LocalTime nextFeedingTime;
*/


    public List<PetDataLog> getPetDataLogs() {
        return petDataLogs;
    }

    public void setPetDataLogs(List<PetDataLog> petDataLogs) {
        this.petDataLogs = petDataLogs;
    }

    public Feeder() {}

    public Feeder(double reservoirLevel, double portion) {
        this.reservoirLevel = reservoirLevel;
        this.portion = portion;

    }

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
                '}';
    }
}
