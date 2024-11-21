package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.*;
import be.kdg.programming3.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class MainController {

    private final PetService petService;
    private final PetDataLogService petDataLogService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final FeederService feederService;


    public MainController(PetService petService, PetDataLogService petDataLogService, ScheduleService scheduleService, UserService userService, FeederService feederService) {
        this.petService = petService;
        this.petDataLogService = petDataLogService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.feederService = feederService;
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



    @GetMapping("/logs")
    public String showPetDataLogs(Model model) {
        List<PetDataLog> petDataLogs = petDataLogService.getAllLogs();
        model.addAttribute("pet_data_log", petDataLogs);
        return "LogDataTestPage";
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
    public Pet createPet(@RequestBody Pet pet) {
        User user = userService.findUserById(pet.getUser().getId());
        pet.setUser(user);

        return petService.save(pet);
    }


    @GetMapping("/data-logs")
    public List<PetDataLog> getAllPetDataLog() {
        return petDataLogService.findAll();
    }


    @GetMapping("/data-logs/{id}")
    public PetDataLog getPetDataLogById(@PathVariable Long id) {
        return petDataLogService.getPetDataLogById(id);
    }

    @PostMapping("/data-logs/add")
    public PetDataLog createPetDataLog(@RequestBody PetDataLog petDataLog) {

        Feeder feeder = feederService.findById(petDataLog.getFeeder().getId());
        Pet pet = petService.findById(petDataLog.getPet().getId());
        // Set the actual entities to the petDataLog object
        petDataLog.setAge(pet.getAge());
        petDataLog.setAnimalType(pet.getAnimalType());

        petDataLog.setFeeder(feeder);
        petDataLog.setPet(pet);

        return petDataLogService.save(petDataLog);
    }

    @DeleteMapping("/data-logs/{id}")
    public void deletePetDataLog(@PathVariable Long id) {
        petDataLogService.deleteById(id);
    }


    @GetMapping("/schedules")
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }

    @PostMapping("/schedules/add")
    public List<Schedule> createSchedules(@RequestBody List<Schedule> schedules) {
        return scheduleService.saveAll(schedules);
    }

    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/users/add")
    public String addUser(@RequestBody User user) {
        Feeder feeder = feederService.findById(user.getFeeder().getId());
        user.setFeeder(feeder);
        userService.save(user);
        return "User added successfully";
    }

    @GetMapping("/registerlogin")
    public String showLoginRegisterPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "registerlogin";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            User user = userService.loginUser(email, password);
            model.addAttribute("user", user);
            return "menupage";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registerlogin";
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            User newUser = userService.registerUser(name, email, password);
            model.addAttribute("user", newUser);
            return "registerlogin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registerlogin";
        }
    }
    //


    @GetMapping("/petchoice")
    public String showPetChoicePage(Model model) {
        model.addAttribute("pets", petService.findAll());
        model.addAttribute("users", userService.findAll());
        return "petchoice";
    }

    @PostMapping("/pets/add-form")
    public String addPet(@RequestParam String name,
                         @RequestParam int age,
                         @RequestParam Breed animalType,
                         @RequestParam double petWeight,
                         Model model) {

        Pet newPet = new Pet(name, age, animalType, petWeight);
        petService.save(newPet);

        model.addAttribute("pets", petService.findAll());
        return "petchoice";
    }
    @GetMapping("/menupage")
    public String showMenuPage(Model model) {
        return "menupage";
    }

    @GetMapping("/feederpage")
    public String showFeederPage(Model model) {
        Feeder feeder = feederService.findById(1L);
        List<Pet> pets = petService.findAll();
        List<Schedule> schedules = scheduleService.findAll();
        model.addAttribute("pets", petService.findAll());
        model.addAttribute("feeders", feederService.findAll());
        model.addAttribute("schedules", scheduleService.findAll());
        return "feederpage";
    }

    @GetMapping("/healthtracker")
    public String showHealthPage(Model model) {
        List<Pet> pets = petService.findAll();
        List<PetDataLog> petdatalogs = petDataLogService.findAll();
        model.addAttribute("pets", petService.findAll());
        model.addAttribute("petdatalogs", petDataLogService.findAll());
        return "healthtracker";
    }
}