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

import java.time.LocalDate;
import java.util.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping
public class MainController {

    //TODO add meaningful logger messages everywhere

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



    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

//    @PostMapping("/users/add")
//    public String addUser(@RequestBody User user) {
//        Feeder feeder = feederService.findById(user.getFeeder().getId());
//        logger.info("aaaaaa");
//
//        if (feeder == null) {
//            return "Feeder with ID " + user.getFeeder().getId() + " does not exist.";
//        }
//        user.setFeeder(feeder);
//
//        Pet pet = petService.findById(user.getPet().getId());
//        if (pet == null) {
//            return "Pet with ID " + user.getPet().getId() + " does not exist.";
//        }
//        user.setPet(pet);
//        userService.save(user);
//        return "User added successfully with Feeder ID: " + feeder.getId() + " and Pet ID: " + pet.getId();
//    }

    @GetMapping("/")
    public String showLoginRegisterPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    //TODO change into view object
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

    //TODO change into view model
    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        userService.save(newUser);
        model.addAttribute("success", "User registered successfully!");
        return "index";
    }


    @GetMapping("/petchoice")
    public String showPetChoicePage(Model model, HttpSession session) {
        if (!isSessionValid(session)) {
            throw new SessionExpiredException("User session has expired.");
        }
        User user = (User) session.getAttribute("user");


        Set<Pet> pets = petService.findPetsByUserId(user.getId()); // Modified `PetService` handles nulls
        model.addAttribute("pets", pets);

//        List<User> users = userService.findAll();

        //TODO move this to a service
        List<User> filteredUsers = new ArrayList<>();
        filteredUsers.add(user);
        petService.findPetsByUserId(user.getId()).forEach(p -> {
            Set<User> users = userService.findUsersByPet(p);
            users.forEach(u -> {if (filteredUsers.stream().noneMatch(o -> Objects.equals(u.getId(), o.getId()))) filteredUsers.add(u);});
        });


        model.addAttribute("users", filteredUsers);

        return "petchoice";
    }


    //TODO change into view object
    @PostMapping("/pets/add-form")
    public String addPet(@RequestParam String name,
                         @RequestParam Long feederId,
                         @RequestParam LocalDate birthDate,
                         @RequestParam Breed animalType,
                         @RequestParam double petWeight,
                         @RequestParam String sex,
                         @RequestParam List<Long> userIds,
                         Model model,
                         HttpSession session) {

        logger.debug("{}",feederId);
        Feeder feeder = feederService.findOrCreateById(feederId);
        logger.debug("{}", feeder);
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



    @GetMapping("/menupage")
    public String showMenuPage(Model model) {
        return "menupage";
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

    @GetMapping("/healthtracker")
    public String showHealthPage(Model model, HttpSession session) {
//        if (!isSessionValid(session)) {
//            throw new SessionExpiredException("User session has expired.");
//        }

        User user = (User) session.getAttribute("user");

        List<Feeder> feeders = new ArrayList<>();
        petService.findPetsByUserId(user.getId()).forEach(pet -> feeders.add(feederService.findByPetId(pet.getId())));

        model.addAttribute("feeders", feeders);
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