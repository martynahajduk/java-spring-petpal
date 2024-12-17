package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.*;
import be.kdg.programming3.exceptions.SessionExpiredException;
import be.kdg.programming3.service.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;

@Controller
@RequestMapping
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    private final PetService petService;
    private final PetDataLogService petDataLogService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final FeederService feederService;


    public PageController(PetService petService, PetDataLogService petDataLogService, ScheduleService scheduleService, UserService userService, FeederService feederService) {
        this.petService = petService;
        this.petDataLogService = petDataLogService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.feederService = feederService;
    }





    @GetMapping("/menupage")
    public String showMenuPage(Model model) {
        return "menupage";
    }


    @GetMapping("/healthtracker")
    public String showHealthPage(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            throw new SessionExpiredException("User session has expired.");
        }
        List<Pet> pets = petService.findAll();
        List<PetDataLog> petdatalogs = petDataLogService.findAll();
        model.addAttribute("pets", petService.findAll());
        model.addAttribute("petdatalogs", petDataLogService.findAll());
        return "healthtracker";
    }

    @GetMapping("/petbreed")
    public String showPetBreeds(Model model) {
        List<Breed> breeds = Arrays.asList(Breed.values());
        model.addAttribute("breeds", breeds);

        return "petbreed";
    }

    @GetMapping("/breed/{breed}")
    public String getBreedDetails(@PathVariable("breed") String breedName, Model model) {
        List<Breed> breeds = Arrays.asList(Breed.values());
        model.addAttribute("breeds", breeds);

        Breed selectedBreed = Arrays.stream(Breed.values())
                .filter(breed -> breed.name().equalsIgnoreCase(breedName))
                .findFirst()
                .orElse(null);

        model.addAttribute("selectedBreed", selectedBreed);

        return "petbreed";
    }

    @GetMapping("/team")
    public String showTeam(Model model) {
        return "team";
    }

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("user") != null;
    }

}