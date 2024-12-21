package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Schedule;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.exceptions.SessionExpiredException;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.PetDataLogService;
import be.kdg.programming3.service.PetService;
import be.kdg.programming3.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping
public class FeederController {
    private static final Logger logger = LoggerFactory.getLogger(FeederController.class);

    private final FeederService feederService;
    private final PetService petService;
    private final PetDataLogService petDataLogService;
    private final ScheduleService scheduleService;

    public FeederController(FeederService feederService, PetService petService, PetDataLogService petDataLogService, ScheduleService scheduleService) {
        this.feederService = feederService;
        this.petService = petService;
        this.petDataLogService = petDataLogService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/feeders")
    public List<Feeder> getAllFeeders() {
        return feederService.findAll();
    }

    @GetMapping("/feeders/{id}")
    public Feeder getFeederById(@PathVariable Long id) {
        return feederService.findById(id);
    }

    @PostMapping("/feeders/add")
    public Feeder createFeeder(@RequestBody Feeder feeder) {
        return feederService.save(feeder);
    }

    @GetMapping("/feederpage")
    public String showFeederPage(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            throw new SessionExpiredException("User session has expired.");
        }
        User user = (User) session.getAttribute("user");

        Set<Feeder> feeders = new HashSet<>();
        user.getPets().forEach(pet -> feeders.add(feederService.findById(pet.getFeeder().getId())));

        Map<Long, List<Schedule>> schedules = new HashMap<>();
        feeders.forEach(feeder -> schedules.put(feeder.getId(), scheduleService.findSchedulesByFeederId(feeder.getId())));

        //get the reservoir level
        Map<Long, Double> reservoirLevels = new HashMap<>();
        feeders.forEach(feeder -> reservoirLevels.put(feeder.getId(), petDataLogService.getFoodLevelPercentageById(feeder.getId())));


        Map<Long, Boolean> isFoodLevelLowMap = new HashMap<>();
        feeders.forEach(feeder -> isFoodLevelLowMap.put(feeder.getId(), reservoirLevels.get(feeder.getId()) <= 20));

        logger.debug("{}", schedules);
        Map<Long, LocalTime> nextFeedingTimes = new HashMap<>();
        schedules.forEach((key, value) -> nextFeedingTimes.put(key, value.stream().filter(s ->
                switch (LocalDate.now().getDayOfWeek()) {
                    case MONDAY -> s.isMonday();
                    case TUESDAY -> s.isTuesday();
                    case WEDNESDAY -> s.isWednesday();
                    case THURSDAY -> s.isThursday();
                    case FRIDAY -> s.isFriday();
                    case SATURDAY -> s.isSaturday();
                    case SUNDAY -> s.isSunday();
                }
        ).sorted(Comparator.comparing(Schedule::getTimeToFeed)).toList().getFirst().getTimeToFeed()));
        model.addAttribute("nextFeedingTimes", nextFeedingTimes);

        model.addAttribute("pets", petService.findAll());
        model.addAttribute("feeders", feeders);
        model.addAttribute("schedules", schedules);
        model.addAttribute("reservoirLevels", reservoirLevels);
        model.addAttribute("isFoodLevelLows", isFoodLevelLowMap);

        return "feederpage";
    }
    @PostMapping("/feedNow")
    public String feedNow( @RequestParam int amount, Long feederId) {
        ArduinoController.feedNow(amount, feederService.findById(feederId));
        return "redirect:/feederpage";
    }

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("user") != null;
    }


}
