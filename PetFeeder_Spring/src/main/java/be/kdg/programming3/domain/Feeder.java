package be.kdg.programming3.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="feeders")
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
    @Column(name = "empty_in")
    private LocalDate emptyIN;
    @ElementCollection
    @CollectionTable(name = "feeder_schedule",
            joinColumns = @JoinColumn(name = "feeder_id"))
    @Column(name = "feeding_time")
    private LocalTime[][] schedule;
    @OneToOne(mappedBy = "feeder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pet pet;

    protected Feeder() {}

    public Feeder(Long id, double reservoirLevel, LocalTime nextFeedingTime, LocalDate nextFeedingDate, LocalDate emptyIN) {
        this.id = id;
        this.reservoirLevel = reservoirLevel;
        this.nextFeedingDate = nextFeedingDate;
        this.nextFeedingTime = nextFeedingTime;
        this.emptyIN = emptyIN;
        this.schedule = new LocalTime[7][];
        schedule[0] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // example schedule for Monday
        schedule[1] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Tuesday
        schedule[2] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Wednesday
        schedule[3] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Thursday
        schedule[4] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Friday
        schedule[5] = new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)}; // Saturday
        schedule[6] = new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)}; // Sunday
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

    public LocalDate getNextFeedingDate() {
        return nextFeedingDate;
    }

    public void setNextFeedingDate(LocalDate nextFeedingDate) {
        this.nextFeedingDate = nextFeedingDate;
    }

    public LocalTime getNextFeedingTime() {
        return nextFeedingTime;
    }

    public void setNextFeedingTime(LocalTime nextFeedingTime) {
        this.nextFeedingTime = nextFeedingTime;
    }

    public LocalDate getEmptyIN() {
        return emptyIN;
    }

    public void setEmptyIN(LocalDate emptyIN) {
        this.emptyIN = emptyIN;
    }

    //get feeding times for a specific day
    public LocalTime[] getFeedingTimesForDay(int dayOfWeek) {
        if (dayOfWeek >= 0 && dayOfWeek < 7) {
            return schedule[dayOfWeek];
        } else {
            throw new IllegalArgumentException("Day of week must be between 0 (Monday) and 6 (Sunday)");
        }
    }

    //set feeding times for a specific day
    public void setFeedingTimesForDay(int dayOfWeek, LocalTime[] times) {
        if (dayOfWeek >= 0 && dayOfWeek < 7) {
            schedule[dayOfWeek] = times;
        } else {
            throw new IllegalArgumentException("Day of week must be between 0 (Monday) and 6 (Sunday)");
        }
    }

    @Override
    public String toString() {
        return "Feeder{" +
                "id=" + id +
                ", reservoirLevel=" + reservoirLevel +
                ", nextFeedingDate=" + nextFeedingDate +
                ", nextFeedingTime=" + nextFeedingTime +
                ", emptyIN=" + emptyIN +
                ", schedule=" + schedule +
                '}';
    }
}
