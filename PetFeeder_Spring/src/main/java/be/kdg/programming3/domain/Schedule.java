package be.kdg.programming3.domain;


import jakarta.persistence.*;

import java.time.LocalTime;

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

    public Schedule(FeedFrequency frequency ,LocalTime timeToFeed,Feeder feeder ) {
        this.frequency = frequency;
        this.timeToFeed = timeToFeed;
        this.feeder = feeder;
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

    public LocalTime getTimeToFeed() {
        return timeToFeed;
    }

    public void setTimeToFeed(LocalTime timeToFeed) {
        this.timeToFeed = timeToFeed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

