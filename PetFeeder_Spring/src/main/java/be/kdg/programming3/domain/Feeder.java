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

    @Column(name="resevoir_level", nullable = false)
    private double reservoirLevel;

    @Column(name = "next_feeding_date")
    private LocalDate nextFeedingDate;

    @Column(name = "next_feeding_time")
    private LocalTime nextFeedingTime;





    protected Feeder() {}

    public Feeder(Long id, double reservoirLevel, LocalTime nextFeedingTime, LocalDate nextFeedingDate, LocalDate emptyIN) {
        this.id = id;
        this.reservoirLevel = reservoirLevel;
        this.nextFeedingDate = nextFeedingDate;
        this.nextFeedingTime = nextFeedingTime;


    }

    @Override
    public String toString() {
        return "Feeder{" +
                "id=" + id +
                ", reservoirLevel=" + reservoirLevel +
                ", nextFeedingDate=" + nextFeedingDate +
                ", nextFeedingTime=" + nextFeedingTime +

                '}';
    }
}
