package be.kdg.programming3.service;

import be.kdg.programming3.domain.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule findById(Long id);
    List<Schedule> findAll();
    Schedule save(Schedule schedule);
    void deleteById(Long id);
    List<Schedule> saveAll(List<Schedule> schedules);
    public List<Schedule> findSchedulesByFeederId(Long feederId);
}
