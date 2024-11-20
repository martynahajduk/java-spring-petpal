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


}

