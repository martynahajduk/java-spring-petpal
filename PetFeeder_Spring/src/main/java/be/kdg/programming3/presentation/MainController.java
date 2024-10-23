package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.*;
import be.kdg.programming3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
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


    @GetMapping("/logs")
    public String showPetDataLogs(Model model) {
        List<PetDataLog> petDataLogs = petDataLogService.getAllLogs();
        model.addAttribute("pet_data_log", petDataLogs); // match the model attribute name in your HTML
        return "LogDataTestPage";  // Ensure this matches your Thymeleaf template name
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
        userService.save(user);
        return "User added successfully";
    }
    /*
    @PostMapping("/")
    @ResponseBody
    public String receiveData(Integer reservoirHeight, Integer bowlWeight, Integer petWeight, Integer id) {
        // Log the received data
        logger.info("reservoirHeight: {}, bowlWeight: {}, petWeight: {}, id: {}", reservoirHeight, bowlWeight, petWeight, id);
        Pet pet = petService.findById(id); // Zakładając, że id odnosi się do id zwierzęcia
        Breed animalType = pet.getAnimalType(); // Załóżmy, że masz metodę, która zwraca typ zwierzęcia

        // Zapisz dane do logu
        PetDataLog petDataLog = new PetDataLog(petWeight, animalType, bowlWeight, pet, null);
        petDataLogService.save(petDataLog);
        // Return a response message
        return "Data received successfully!";
    }

     */
}
