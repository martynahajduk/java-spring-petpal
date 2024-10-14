package be.kdg.programming3.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Feeder {
    double reservoirLevel;
    LocalDate nextFeedingDate;
    LocalTime nextFeedingTime;
    LocalDate emptyIN;
    LocalTime[][] schedule;


    public Feeder(double reservoirLevel, LocalTime nextFeedingTime, LocalDate nextFeedingDate, LocalDate emptyIN) {
        this.reservoirLevel = reservoirLevel;
        this.nextFeedingDate = nextFeedingDate;
        this.nextFeedingTime = nextFeedingTime;
        this.emptyIN = emptyIN;


        this.schedule = new LocalTime[7][];
        schedule[0] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Monday
        schedule[1] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Tuesday
        schedule[2] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Wednesday
        schedule[3] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Thursday
        schedule[4] = new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(18, 0)}; // Friday
        schedule[5] = new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)}; // Saturday
        schedule[6] = new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)}; // Sunday

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

    public LocalTime[] getFeedingTimesForDay(int dayOfWeek) {
        return schedule[dayOfWeek];
    }
}
