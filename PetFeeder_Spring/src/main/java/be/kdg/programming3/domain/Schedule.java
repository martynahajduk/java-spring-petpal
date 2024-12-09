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
    private double portion;

    private boolean monday = false;
    private boolean tuesday = false;
    private boolean wednesday = false;
    private boolean thursday = false;
    private boolean friday = false;
    private boolean saturday = false;
    private boolean sunday = false;

    public Schedule() {}

    public Schedule(LocalTime timeToFeed, Feeder feeder, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.timeToFeed = timeToFeed;
        this.feeder = feeder;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public Feeder getFeeder() {
        return feeder;
    }

    public void setFeeder(Feeder feeder) {
        this.feeder = feeder;
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

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }
}

