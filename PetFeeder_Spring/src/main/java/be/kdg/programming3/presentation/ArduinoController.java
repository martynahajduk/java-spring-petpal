package be.kdg.programming3.presentation;


import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.FeederService;
import be.kdg.programming3.service.PetDataLogService;
import be.kdg.programming3.service.PetService;
import be.kdg.programming3.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArduinoController {

    private final static Logger logger = LoggerFactory.getLogger(ArduinoController.class);
    private FeederService feederService;
    private PetService petService;


    public ArduinoController(FeederService feederService, PetService petService) {
        this.feederService = feederService;
        this.petService = petService;

    }

    @PostMapping("/SensorData")
    @ResponseBody
    public String receiveData( Double reservoirHeight, Double bowlWeight, Double petWeight, Double id) {

        try {
            // Validate parameters
            if (reservoirHeight == null || bowlWeight == null || petWeight == null || id == null) {
                return "Invalid data received!";
            }
        // Log the received data
        logger.info("reservoirHeight: {}, bowlWeight: {}, petWeight: {}, id: {}", reservoirHeight, bowlWeight, petWeight, id);
            petService.save(new Pet(petWeight));
       // petService.save(new Pet("name", 5, Breed.HAMSTER, "female", petWeight));
        logger.debug("save2");
        feederService.save(new Feeder(reservoirHeight,bowlWeight));
        logger.debug("save1");
        // Return a response message
        return "Data received successfully!";
        } catch (Exception e) {
            logger.error("Error while processing data: {}", e.getMessage());
            return "An error occurred while processing the data!";
        }
    }



}


