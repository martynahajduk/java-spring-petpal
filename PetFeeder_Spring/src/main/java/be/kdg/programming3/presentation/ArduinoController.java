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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
public class ArduinoController {
    private static final Logger logger = LoggerFactory.getLogger(ArduinoController.class);
    private final PetDataLogService petDataLogService;
    private static String ip = "192.168.0.159";
    private final ArduinoService arduinoService;
    ;

    // Constructor-based dependency injection
    public ArduinoController(PetDataLogService petDataLogService, ArduinoService arduinoService) {
        this.petDataLogService = petDataLogService;
        this.arduinoService = arduinoService;

    }

    @GetMapping("/view-data")       //? should this be in arduino controller?
    public String viewData(Model model) {
        List<PetDataLog> petDataLogs = petDataLogService.findAll();

        // Add data to the model
        model.addAttribute("petData", petDataLogs);

        return "TestPage"; // Name of the Thymeleaf template
    }

    @PostMapping(value = "/SensorData", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public String receiveData(
            @RequestParam("reservoirHeight") Double reservoirHeight,
            @RequestParam("bowlWeight") Double bowlWeight,
            @RequestParam("petWeight") Double petWeight,
            @RequestParam(required = false) Long feederId,
            @RequestParam("msgId") Double msgId
    ) {
        try {
            logger.info("Received Data -> reservoirHeight: {}, bowlWeight: {}, petWeight: {}, id: {}",
                    reservoirHeight, bowlWeight, petWeight, feederId);

            // Delegate processing to the ArduinoService
            arduinoService.processSensorData(reservoirHeight, bowlWeight, petWeight, feederId);

            return "Data received and processed successfully!";
        } catch (Exception e) {
            logger.error("Error while processing data: {}", e.getMessage(), e);
            return "An error occurred while processing the data!";
        }
    }

    public static void sendSchedule(List<Long> times, List<Integer> amount) {
//        List<Long> times = List.of((long)5000, (long)20000);
//        List<Integer> amount = List.of(20, 20);
        // Define the request payload, if any
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
    }

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    public static String zero() {  //? should we use HttpClient or the one shown below?

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setContentLength(requestPayload.length());

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Set the URL of the API endpoint
        String apiUrl = "http://" + ip + "/zero";

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

        LocalDateTime date = LocalDateTime.now();
        System.out.println("Date = " + date);
        LocalDateTime start = date;

        while (start.getDayOfWeek() != DayOfWeek.MONDAY) {
            start = start.minusDays(1);
        }
        while (start.getHour() > 0) {
            start = start.minusHours(1);
        }
        while (start.getMinute() > 0) {
            start = start.minusMinutes(1);
        }
        while (start.getSecond() > 0) {
            start = start.minusSeconds(1);
        }

        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long seconds = LocalDateTime.now().atZone(zoneId).toEpochSecond() - start.atZone(zoneId).toEpochSecond();
        // Return a response message
        return String.valueOf(seconds);
    }

    @PostMapping("/feedNow")
    public String feedNow( @RequestParam int amount) {
        // Define the request payload, if any
        String requestPayload = String.format("amount=%s", amount);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentLength(requestPayload.length());

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestPayload, headers);

        // Set the URL of the API endpoint
        String apiUrl = "http://" + ip + "/feedNow";

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
        return "redirect:/feederpage";
    }
}


