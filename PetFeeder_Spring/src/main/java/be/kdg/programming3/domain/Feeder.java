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

    @Column(name = "empty_in")
    private LocalDate emptyIN;

    @ElementCollection
    @CollectionTable(name = "feeder_schedule", joinColumns = @JoinColumn(name = "feeder_id"))
    @Column(name = "feeding_time")
    private List<String> schedule = new ArrayList<>();

    @OneToOne(mappedBy = "feeder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pet pet;

    protected Feeder() {}

    public Feeder(Long id, double reservoirLevel, LocalTime nextFeedingTime, LocalDate nextFeedingDate, LocalDate emptyIN) {
        this.id = id;
        this.reservoirLevel = reservoirLevel;
        this.nextFeedingDate = nextFeedingDate;
        this.nextFeedingTime = nextFeedingTime;
        this.emptyIN = emptyIN;

        // Initialize schedule with 7 days (one list for each day)
      // schedule = new ArrayList<>(7);
      // for (int i = 0; i < 7; i++) {
      //     schedule.add();
      // }

      // // Example schedule for Monday and Tuesday
      // schedule.get(0).add(LocalTime.of(8, 0)); // Monday
      // schedule.get(0).add(LocalTime.of(18, 0));
      // schedule.get(1).add(LocalTime.of(9, 0)); // Tuesday
      // schedule.get(1).add(LocalTime.of(17, 0));
    }

    // Other getters and setters

  // public List<LocalTime> getFeedingTimesForDay(int dayOfWeek) {
  //     if (dayOfWeek >= 0 && dayOfWeek < 7) {
  //         return schedule.get(dayOfWeek);
  //     } else {
  //         throw new IllegalArgumentException("Day of week must be between 0 (Monday) and 6 (Sunday)");
  //     }
  // }

  // public void setFeedingTimesForDay(int dayOfWeek, List<LocalTime> times) {
  //     if (dayOfWeek >= 0 && dayOfWeek < 7) {
  //         schedule.set(dayOfWeek, times);
  //     } else {
  //         throw new IllegalArgumentException("Day of week must be between 0 (Monday) and 6 (Sunday)");
  //     }
  // }

    @Override
    public String toString() {
        return "Feeder{" +
                "id=" + id +
                ", reservoirLevel=" + reservoirLevel +
                ", nextFeedingDate=" + nextFeedingDate +
                ", nextFeedingTime=" + nextFeedingTime +
                ", emptyIN=" + emptyIN +
                '}';
    }
}
