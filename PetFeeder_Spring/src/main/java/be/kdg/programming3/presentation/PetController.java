package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.exceptions.SessionExpiredException;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.PetService;
import be.kdg.programming3.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping
public class PetController {
    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    private final FeederService feederService;
    private final PetService petService;
    private final UserService userService;

    public PetController(FeederService feederService, PetService petService, UserService userService) {
        this.feederService = feederService;
        this.petService = petService;
        this.userService = userService;
    }

    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return petService.findAll();
    }

    @GetMapping("/pets/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.findById(id);
    }

    @PostMapping("/pets/add")
    public String createPet(@RequestBody Pet pet) {
        Set<User> users = new HashSet<>();

        for (User user : pet.getUsers()) {
            User existingUser = userService.findUserById(user.getId());
            if (existingUser != null) {
                users.add(existingUser);
            }
        }
        pet.setUsers(users);
        petService.save(pet);
        return pet.toString();
    }

    @PostMapping("/pets/add-form")
    public String addPet(
            @RequestParam String name,
            @RequestParam Long feederId,
            @RequestParam LocalDate birthDate,
            @RequestParam Breed animalType,
            @RequestParam double petWeight,
            @RequestParam String sex,
            @RequestParam List<Long> userIds,
                         Model model,
                         HttpSession session) {




        Feeder feeder = feederService.findOrCreateById(feederId);
        logger.debug("Feeder: {}", feeder);
        Pet newPet = new Pet(name, feeder, birthDate, animalType, petWeight, sex, new HashSet<>());
        feeder.setPet(newPet);
        newPet.setAgeWeeks(newPet.calculateAgeWeeks());
        petService.save(newPet);

        for (Long userId : userIds) {
            User user = userService.findUserById(userId);
            if (user != null) {
                user.addPet(newPet);
                userService.save(user);
            }
        }
        User user = (User) session.getAttribute("user");
        user.addPet(newPet);
        session.setAttribute("user", user);

        return "redirect:/petchoice";
    }


    @GetMapping("/petchoice")
    public String showPetChoicePage(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            throw new SessionExpiredException("User session has expired.");
        }
        User user = (User) session.getAttribute("user");


        Set<Pet> pets = petService.findPetsByUserId(user.getId()); // Modified `PetService` handles nulls
        model.addAttribute("pets", pets);
        List<User> filteredUsers = new ArrayList<>();
        filteredUsers.add(user);
        petService.findPetsByUserId(user.getId()).forEach(p -> {
            Set<User> users = userService.findUsersByPet(p);
            users.forEach(u -> {if (filteredUsers.stream().noneMatch(o -> Objects.equals(u.getId(), o.getId()))) filteredUsers.add(u);});
        });


        model.addAttribute("users", filteredUsers);

        return "petchoice";
    }

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("user") != null;
    }



}
