package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.*;
import be.kdg.programming3.service.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import be.kdg.programming3.presentation.ArduinoController;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final PetService petService;
    private final PetDataLogService petDataLogService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final FeederService feederService;
    private final ArduinoController arduinoController;


    public MainController(PetService petService, PetDataLogService petDataLogService, ScheduleService scheduleService, UserService userService, FeederService feederService, ArduinoController arduinoController) {
        this.petService = petService;
        this.petDataLogService = petDataLogService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.feederService = feederService;
        this.arduinoController = arduinoController;
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


    @GetMapping("/data-logs")
    public List<PetDataLog> getAllPetDataLog() {
        return petDataLogService.findAll();
    }


    @GetMapping("/data-logs/{id}")
    public PetDataLog getPetDataLogById(@PathVariable Long id) {
        return petDataLogService.getPetDataLogById(id);
    }

    //? Is someone going to use this method
//    @PostMapping("/data-logs/add")
//    public PetDataLog createPetDataLog(@RequestBody PetDataLog petDataLog) {
//
//        Feeder feeder = feederService.findById(petDataLog.getFeeder().getId());
//        Pet pet = petService.findById(petDataLog.getId());
//        petDataLog.setAgeWeeks(pet.getAgeWeeks());
//        petDataLog.setBreed(pet.getAnimalType().toString());
//
//        petDataLog.setFeeder(feeder);
//        petDataLog.setPet(pet);
//
//        return petDataLogService.save(petDataLog);
//    }

    @PostMapping("/data-logs/{id}")
    public PetDataLog getPetDataLohById(@PathVariable Long id) {return petDataLogService.getPetDataLogById(id);}

    @DeleteMapping("/data-logs/{id}")
    public void deletePetDataLog(@PathVariable Long id) {
        petDataLogService.deleteById(id);
    }


    @GetMapping("/schedules")
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }


    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
    }

    @GetMapping("/schedulecreation")
    public String showScheduleCreationPage(Model model) {
        model.addAttribute("feeders", feederService.findAll());
        model.addAttribute("frequencies", FeedFrequency.values());
        model.addAttribute("schedules", scheduleService.findAll());

        //TODO add day choices for weekly feeding schedule, figure something out for monthly
        //? We can also disable it for now and add it later, a biweekly option is also possible

        return "schedule";
    }

    @PostMapping("/schedule/add")
    public String handleScheduleCreation(
            @RequestParam Long feederId,
            @RequestParam String timeToFeed,
            @RequestParam FeedFrequency frequency,
            @RequestParam double portion,  // Added portion size parameter
            Model model) {

        Feeder feeder = feederService.findById(feederId);
        if (feeder == null) {
            model.addAttribute("errorMessage", "Invalid feeder selected!");
            return "schedulecreation";
        }

        LocalTime feedTime;
        try {
            feedTime = LocalTime.parse(timeToFeed);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid time format! Use HH:mm.");
            return "schedulecreation";
        }

        Schedule newSchedule = new Schedule();
        newSchedule.setFeeder(feeder);
        newSchedule.setTimeToFeed(feedTime);
        newSchedule.setFrequency(frequency);
        newSchedule.setPortion(portion);  // Set the portion size
//        arduinoController.sendSchedule(feedTime,(int)portion);

        //TODO add method that converts daily feeding to weekly feeding
        //? just an idea:
        List<Long> feedTimeList = new ArrayList<>();
//        long time = feedTime.getLong(ChronoField.SECOND_OF_DAY);
//         if (frequency == FeedFrequency.DAILY) {
//             for (int i = 0; i < 6; i++) {
//                 feedTimeList.add(time + i*86400);   // can be 2 times to add
//             }
//         }else{
//             feedTimeList.add(time);  // need a list of feeding times here to add
//         }
//

        model.addAttribute("successMessage", "Schedule created successfully!");
        model.addAttribute("feeders", feederService.findAll());
        model.addAttribute("frequencies", FeedFrequency.values());
        scheduleService.save(newSchedule);
        arduinoController.sendSchedule(feedTime,(int)portion);

        return "redirect:/schedulecreation";
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
        logger.info("aaaaaa");

        if (feeder == null) {
            return "Feeder with ID " + user.getFeeder().getId() + " does not exist.";
        }
        user.setFeeder(feeder);

        Pet pet = petService.findById(user.getPet().getId());
        if (pet == null) {
            return "Pet with ID " + user.getPet().getId() + " does not exist.";
        }
        user.setPet(pet);
        userService.save(user);
        return "User added successfully with Feeder ID: " + feeder.getId() + " and Pet ID: " + pet.getId();
    }

    @GetMapping("/")
    public String showLoginRegisterPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = userService.loginUser(email, password);
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "menupage";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam(required = false) Long feederId,
                               Model model) {
        Feeder feeder = null;

        if (feederId != null) {
            feeder = feederService.findById(feederId);
        } else {
            feeder = feederService.save(new Feeder());
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFeeder(feeder);

        userService.save(newUser);
        model.addAttribute("success", "User registered successfully!");
        return "index";
    }


    @GetMapping("/petchoice")
    public String showPetChoicePage(Model model) {
        List<Pet> pets = petService.findAll();
        List<User> users = userService.findAll();

        model.addAttribute("pets", pets);
        model.addAttribute("users", users);

        return "petchoice";
    }

    @PostMapping("/pets/add-form")
    public String addPet(@RequestParam String name,
                         @RequestParam LocalDate birthDate,
                         @RequestParam Breed animalType,
                         @RequestParam double petWeight,
                         @RequestParam String sex,
                         @RequestParam List<Long> userIds,
                         Model model) {

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


        //get teh reservoir level
        Double reservoirLevel = petDataLogService.getFoodLevelPercentage(feeder.getId());

        boolean isFoodLevelLow = reservoirLevel <= 20;

        model.addAttribute("pets", petService.findAll());
        model.addAttribute("feeders", feederService.findAll());
        model.addAttribute("schedules", scheduleService.findAll());
        model.addAttribute("reservoirLevel", reservoirLevel);
        model.addAttribute("isFoodLevelLow", isFoodLevelLow);

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

}