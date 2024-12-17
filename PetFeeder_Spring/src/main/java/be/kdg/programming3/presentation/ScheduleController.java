package be.kdg.programming3.presentation;


import be.kdg.programming3.domain.*;
import be.kdg.programming3.exceptions.SessionExpiredException;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final FeederService feederService;
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    public ScheduleController(ScheduleService scheduleService, FeederService feederService) {
        this.scheduleService = scheduleService;
        this.feederService = feederService;
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
        Feeder feeder = feederService.findById(user.getFeeder().getId());
        model.addAttribute("feeders", feeder);
        model.addAttribute("frequencies", FeedFrequency.values());
        model.addAttribute("schedules", scheduleService.findSchedulesByFeederId(feeder.getId()));

        model.addAttribute("daysOfWeek", DayOfWeek.values());
        model.addAttribute("scheduleObject", new Schedule());

        return "schedule";
    }

    //TODO change into view object and converter
    @PostMapping("/schedule/add")
    public String handleScheduleCreation(
            Schedule schedule,
            Model model) {

        logger.debug(schedule.toString());

        if (schedule.getFeeder() == null) {
            model.addAttribute("errorMessage", "Invalid feeder selected!");
            return "redirect:/schedulecreation";
        }

        //TODO:
        //sql Select frequency, portion, timetofeed where feederid = x
        //put in a list
        //modify list to chronical whole schedule

        List<Long> feedTimeList = new ArrayList<>();
//        long time = feedTime.getLong(ChronoField.SECOND_OF_DAY);
//         if (frequency == FeedFrequency.DAILY) {
//             for (int i = 0; i < 6; i++) {
//                 feedTimeList.add(time + i*86400);   // can be 2 times to add
//             }
//         }else{
//             feedTimeList.add(time);  // need a list of feeding times here to add
//         }


        model.addAttribute("successMessage", "Schedule created successfully!");
        model.addAttribute("feeders", feederService.findAll());
        model.addAttribute("frequencies", FeedFrequency.values());
        scheduleService.save(schedule);
//        arduinoController.sendSchedule(feedTime,(int)portion);

        return "redirect:/schedulecreation";
    }

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("user") != null;
    }

}
