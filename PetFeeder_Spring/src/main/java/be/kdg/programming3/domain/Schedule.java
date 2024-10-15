package be.kdg.programming3.domain;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Schedule {
    public List<LocalDate> getTimeToFeed() {
        return timeToFeed;
    }

    public void setTimeToFeed(List<LocalDate> timeToFeed) {
        this.timeToFeed = timeToFeed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<LocalDate> timeToFeed;

    public Schedule() {

    }

    public void setId(Long id) {this.id = id;}

    public Long getId() {return id;}
}
