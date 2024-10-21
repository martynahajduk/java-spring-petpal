package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.*;
import be.kdg.programming3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final FeederService feederService;
    private final PetService petService;
    private final PetDataLogService petDataLogService;
    private final ScheduleService scheduleService;
    private final UserService userService;

    public MainController(FeederService feederService, PetService petService, PetDataLogService petDataLogService, ScheduleService scheduleService, UserService userService) {
        this.feederService = feederService;
        this.petService = petService;
        this.petDataLogService = petDataLogService;
        this.scheduleService = scheduleService;
        this.userService = userService;
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
        return petService.save(pet);
    }





    @GetMapping("/pet-data-logs")
    public List<PetDataLog> getAllPetDataLog() {
        return petDataLogService.findAll();
    }


    @GetMapping("/pet-data-logs/{id}")
    public PetDataLog getPetDataLogById(@PathVariable Long id) {
        return petDataLogService.getPetDataLogById(id);
    }

    @PostMapping("/pet-data-logs/add")
    public PetDataLog createPetDataLog(@RequestBody PetDataLog petDataLog) {
        return petDataLogService.save(petDataLog);
    }

    @DeleteMapping("/pet-data-logs/{id}")
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
        userService.save(user);
        return "User added successfully";
    }
}
