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

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

   /* @GetMapping("/schedule")
    public String getSchedule(Model model) {
        Schedule schedule = ... // fetch your schedule entity
        List<String> formattedTimes = scheduleService.getFormattedFeedingTimes(schedule);

        model.addAttribute("feedingTimes", formattedTimes);
        return "schedule"; // your Thymeleaf view
    }
*/
    /*@PostMapping("/add")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule createdSchedule = scheduleService.save(schedule);
        return ResponseEntity.ok(createdSchedule);
    }
     */
    @PostMapping("/add")
    public ResponseEntity<List<Schedule>> createSchedules(@RequestBody List<Schedule> schedules) {
        List<Schedule> savedSchedules = scheduleService.saveAll(schedules);
        return ResponseEntity.ok(savedSchedules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
