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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class FeederController {
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

        Feeder feeder = feederService.findById(user.getFeeder().getId());
        List<Schedule> schedules = scheduleService.findSchedulesByFeederId(feeder.getId());


        //get teh reservoir level
        Double reservoirLevel = petDataLogService.getFoodLevelPercentage(feeder.getId());

        boolean isFoodLevelLow = reservoirLevel <= 20;

        model.addAttribute("pets", petService.findAll());
        model.addAttribute("feeders", feeder);
        model.addAttribute("schedules", schedules);
        model.addAttribute("reservoirLevel", reservoirLevel);
        model.addAttribute("isFoodLevelLow", isFoodLevelLow);

        return "feederpage";
    }
    @PostMapping("/feedNow")
    public String feedNow( @RequestParam int amount) {
        ArduinoController.feedNow(amount);
        return "redirect:/feederpage";
    }
    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("user") != null;
    }


}
