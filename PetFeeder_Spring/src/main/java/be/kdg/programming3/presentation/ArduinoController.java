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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Controller
public class ArduinoController {
    private static final Logger logger = LoggerFactory.getLogger(ArduinoController.class);
    private final DataProcessorFactory dataProcessorFactory;
    private final PetDataLogService petDataLogService;
    private static String ip = "192.168.0.159";

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

    public String receiveSchedule(List<Long> times, List<Integer> amount) {
//        List<Long> times = List.of((long)5000, (long)20000);
//        List<Integer> amount = List.of(20, 20);
//        // Define the request payload, if any
        String requestPayload = String.format("times=%s&amount=%s", times, amount).replace(" ","").replace("[","").replace("]","");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentLength(requestPayload.length());

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestPayload, headers);

        // Set the URL of the API endpoint
        String apiUrl = "http://" + ip + "/schedule";

        URI uri = URI.create(apiUrl);

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        logger.info("oi");
        // Send the POST request
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(uri, requestEntity, String.class);

        // Handle the response
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            logger.info("Response body: " + responseBody);
        } else {
            logger.error("POST request failed with status code: " + responseEntity.getStatusCodeValue());
        }
        logger.info("oi2");
        return "send";
    }


    @PostMapping("/IP")
    @ResponseBody
    public String receiveIP(String address) {
        // Log the received data
        logger.info("IP: {}", address);
        ip = address;

        // Return a response message
        return "IP received successfully!";
    }
}


