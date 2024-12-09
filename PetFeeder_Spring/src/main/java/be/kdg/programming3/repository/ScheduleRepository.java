package be.kdg.programming3.repository;

import be.kdg.programming3.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByFeederId(Long feederId);
}
