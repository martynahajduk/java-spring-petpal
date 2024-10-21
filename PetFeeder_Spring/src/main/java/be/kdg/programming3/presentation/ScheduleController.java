package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Schedule;
import be.kdg.programming3.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

 //  private final ScheduleService scheduleService;

 //  @Autowired
 //  public ScheduleController(ScheduleService scheduleService) {
 //      this.scheduleService = scheduleService;
 //  }

 //  @GetMapping
 //  public List<Schedule> getAllSchedules() {
 //      return scheduleService.findAll();
 //  }

 //  @PostMapping("/add")
 //  public List<Schedule> createSchedules(@RequestBody List<Schedule> schedules) {
 //      return scheduleService.saveAll(schedules);
 //  }

 //  @DeleteMapping("/{id}")
 //  public void deleteSchedule(@PathVariable Long id) {
 //      scheduleService.deleteById(id);
 //  }
}
