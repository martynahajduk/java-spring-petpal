package be.kdg.programming3.domain;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Schedule {
    public List<LocalDateTime> getTimeToFeed() {
        return timeToFeed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<LocalDateTime> timeToFeed;
    @ManyToOne
    @JoinColumn(name = "feeder_id", nullable = false)
    private Feeder feeder;

    public Schedule() {}

    public void setId(Long id) {this.id = id;}

    public Long getId() {
        return id;
    }

    public void setTimeToFeed(List<LocalDateTime> timeToFeed) {
        this.timeToFeed = timeToFeed;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
    }
}
