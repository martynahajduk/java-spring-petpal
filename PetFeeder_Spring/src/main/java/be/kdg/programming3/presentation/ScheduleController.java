package be.kdg.programming3.presentation;


import be.kdg.programming3.domain.*;
import be.kdg.programming3.exceptions.SessionExpiredException;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.ScheduleService;
import be.kdg.programming3.service.ScheduleServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping
public class ScheduleController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;
    private final FeederService feederService;
    private final ScheduleServiceImpl scheduleServiceImpl;
    private final ArduinoController arduinoController;

    public ScheduleController(ScheduleService scheduleService, FeederService feederService, ScheduleServiceImpl scheduleServiceImpl, ArduinoController arduinoController) {
        this.scheduleService = scheduleService;
        this.feederService = feederService;
        this.scheduleServiceImpl = scheduleServiceImpl;
        this.arduinoController = arduinoController;
    }


    @GetMapping("/schedules")
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }
    @GetMapping("/deleteSchedule/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return "redirect:/schedulecreation";
    }

    @GetMapping("/schedulecreation")
    public String showScheduleCreationPage(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            throw new SessionExpiredException("User session has expired.");
        }
        User user = (User) session.getAttribute("user");

        Set<Feeder> feeders = new HashSet<>();
        user.getPets().forEach(pet -> feeders.add(feederService.findById(pet.getFeeder().getId())));
        model.addAttribute("feeders", feeders);
        logger.debug("{}",feeders);

        Map<Long, List<Schedule>> schedules = new HashMap<>();
        feeders.forEach(feeder -> schedules.put(feeder.getId(), scheduleService.findSchedulesByFeederId(feeder.getId())));
        model.addAttribute("schedules", schedules);

        model.addAttribute("daysOfWeek", DayOfWeek.values());
        model.addAttribute("scheduleObject", new Schedule());

        return "schedule";
    }

    @PostMapping("/schedule/add")
    public String handleScheduleCreation(
            Schedule schedule,
            Model model) {

        logger.debug(schedule.toString());

        if (schedule.getFeeder() == null) {
            model.addAttribute("errorMessage", "Invalid feeder selected!");
            return "redirect:/schedulecreation";
        }

        scheduleService.save(schedule);

        List<Long> feedTimeList = new ArrayList<>();
        List<Double> amountList = new ArrayList<>();

        List<Schedule> schedules = scheduleService.findSchedulesByFeederId(schedule.getFeeder().getId());
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            for (Schedule scheduleIteration : schedules) {
                switch (dayOfWeek) {
                    case MONDAY:
                        if (scheduleIteration.isMonday()) {
                            feedTimeList.add((long) scheduleIteration.getTimeToFeed().toSecondOfDay());
                            amountList.add(scheduleIteration.getPortion());
                        }
                        break;
                    case TUESDAY:
                        if (scheduleIteration.isTuesday()) {
                            feedTimeList.add((long) scheduleIteration.getTimeToFeed().toSecondOfDay());
                            amountList.add(scheduleIteration.getPortion());
                        }
                        break;
                    case WEDNESDAY:
                        if (scheduleIteration.isWednesday()) {
                            feedTimeList.add((long) scheduleIteration.getTimeToFeed().toSecondOfDay());
                            amountList.add(scheduleIteration.getPortion());
                        }
                        break;
                    case THURSDAY:
                        if (scheduleIteration.isThursday()) {
                            feedTimeList.add((long) scheduleIteration.getTimeToFeed().toSecondOfDay());
                            amountList.add(scheduleIteration.getPortion());
                        }
                        break;
                    case FRIDAY:
                        if (scheduleIteration.isFriday()) {
                            feedTimeList.add((long) scheduleIteration.getTimeToFeed().toSecondOfDay());
                            amountList.add(scheduleIteration.getPortion());
                        }
                        break;
                    case SATURDAY:
                        if (scheduleIteration.isSaturday()) {
                            feedTimeList.add((long) scheduleIteration.getTimeToFeed().toSecondOfDay());
                            amountList.add(scheduleIteration.getPortion());
                        }
                        break;
                    case SUNDAY:
                        if (scheduleIteration.isSunday()) {
                            feedTimeList.add((long) scheduleIteration.getTimeToFeed().toSecondOfDay());
                            amountList.add(scheduleIteration.getPortion());
                        }
                        break;
                }
            }
        }

        model.addAttribute("successMessage", "Schedule created successfully!");
        model.addAttribute("feeders", feederService.findAll());
        model.addAttribute("frequencies", FeedFrequency.values());
        arduinoController.sendSchedule(feedTimeList, amountList, schedule.getFeeder());

        return "redirect:/schedulecreation";
    }

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("user") != null;
    }

}
