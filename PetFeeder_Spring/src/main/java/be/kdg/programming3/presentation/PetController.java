package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.User;
import be.kdg.programming3.exceptions.SessionExpiredException;
import be.kdg.programming3.service.PetService;
import be.kdg.programming3.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping
public class PetController {
    private final PetService petService;
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
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
    public String addPet(@RequestParam String name,
                         @RequestParam LocalDate birthDate,
                         @RequestParam Breed animalType,
                         @RequestParam double petWeight,
                         @RequestParam String sex,
                         @RequestParam List<Long> userIds,
                         Model model,
                         HttpSession session) {


        Pet newPet = new Pet(name, birthDate, animalType, petWeight, sex,  new HashSet<>());
        newPet.setAgeWeeks(newPet.calculateAgeWeeks());
        petService.save(newPet);
        for (Long userId : userIds) {
            User user = userService.findUserById(userId);
            if (user != null) {
                user.setPet(newPet);
                userService.save(user);
            }
        }
        User userHttp = (User) session.getAttribute("user");
        Pet pet = petService.getPetByUserId(userHttp.getId());
        model.addAttribute("pets", pet);
        return "petchoice";
    }
    @GetMapping("/petchoice")
    public String showPetChoicePage(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            throw new SessionExpiredException("User session has expired.");
        }
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
        Long feederId = user.getFeeder().getId();

        Pet pet = petService.getPetByUserId(user.getId()); // Modified `PetService` handles nulls
        model.addAttribute("pets", pet);

//        List<User> users = userService.findAll();

        List<User> filteredUsers = userService.findUsersByFeederId(feederId);

        model.addAttribute("users", filteredUsers);

        return "petchoice";
    }

    private boolean isSessionValid(HttpSession session) {
        return session.getAttribute("user") != null;
    }



}
