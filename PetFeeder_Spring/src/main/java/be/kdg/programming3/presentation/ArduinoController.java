package be.kdg.programming3.presentation;


import be.kdg.programming3.collector.ArduinoSensorData;
import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.Pet;
import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.processor.DataProcessor;
import be.kdg.programming3.processor.DataProcessorFactory;
import be.kdg.programming3.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArduinoController {
    private static final Logger logger = LoggerFactory.getLogger(ArduinoController.class);
    private final DataProcessorFactory dataProcessorFactory;
    private final PetDataLogService petDataLogService;

    // Constructor-based dependency injection
    public ArduinoController( DataProcessorFactory dataProcessorFactory, PetDataLogService petDataLogService) {
        this.dataProcessorFactory = dataProcessorFactory;
        this.petDataLogService = petDataLogService;
    }

    @GetMapping("/view-data")
    public String viewData(Model model) {
        List<PetDataLog> petDataLogs = petDataLogService.findAll();

        // Add data to the model
        model.addAttribute("petData", petDataLogs);

        return "TestPage"; // Name of the Thymeleaf template
    }

    @PostMapping("/SensorData")
    @ResponseBody
    public String receiveData(@RequestBody ArduinoSensorData data) {
        try {
            logger.info("Received data: {}", data);

            // Get the appropriate processor and delegate processing
            DataProcessor processor = dataProcessorFactory.getProcessor(data);
            //controller automatically will use composite processor when requesting a processor from the factory
            processor.saveToDatabase(data);

            return "Data received and processed successfully!";
        } catch (Exception e) {
            logger.error("Error while processing data: {}", e.getMessage());
            return "An error occurred while processing the data!";
        }
    }
}


