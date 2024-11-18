package be.kdg.programming3.domain;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Many Schedules can belong to one Feeder
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "feeder_id")
    private Feeder feeder;  // Many-to-One relationship with Feeder
    private LocalTime timeToFeed;

    @Enumerated(EnumType.STRING)
    private FeedFrequency frequency;

    public Schedule() {}

    public Schedule(Feeder feeder, LocalTime timeToFeed, FeedFrequency frequency) {
        this.feeder = feeder;
        this.timeToFeed = timeToFeed;
        this.frequency = frequency;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
    }

    public FeedFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(FeedFrequency frequency) {
        this.frequency = frequency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTimeToFeed() {
        return timeToFeed;
    }




    public void setTimeToFeed(LocalTime timeToFeed) {
        this.timeToFeed = timeToFeed;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", timeToFeed=" + timeToFeed +
                '}';
    }
}

