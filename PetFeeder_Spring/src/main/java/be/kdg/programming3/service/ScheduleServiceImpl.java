package be.kdg.programming3.service;

import be.kdg.programming3.domain.Schedule;
import be.kdg.programming3.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;


    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> saveAll(List<Schedule> schedules) {
        return scheduleRepository.saveAll(schedules);
    }

    public List<Schedule> findSchedulesByFeederId(Long feederId) {
       return scheduleRepository.findByFeederId(feederId);
    }

}
