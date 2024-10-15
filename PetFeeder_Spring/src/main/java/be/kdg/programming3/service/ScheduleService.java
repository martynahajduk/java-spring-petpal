package be.kdg.programming3.service;

import be.kdg.programming3.domain.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAll();
    Schedule save(Schedule schedule);
    void deleteById(Long id);
}
