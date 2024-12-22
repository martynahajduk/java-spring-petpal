package be.kdg.programming3.presentation;


import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class ArduinoController {
    private static final Logger logger = LoggerFactory.getLogger(ArduinoController.class);
    private final PetDataLogService petDataLogService;
    private final ArduinoService arduinoService;
    private final FeederService feederService;
    ;

    // Constructor-based dependency injection
    public ArduinoController(PetDataLogService petDataLogService, ArduinoService arduinoService, FeederService feederService) {
        this.petDataLogService = petDataLogService;
        this.arduinoService = arduinoService;
        this.feederService = feederService;
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

    public  void sendSchedule(List<Long> times, List<Double> amount, Feeder feeder) {
        logger.debug("{}",times);
        // Define the request payload, if any
        String requestPayload = String.format("times=%s&amount=%s", times, amount).replace(" ","").replace("[","").replace("]","");

        sendData(requestPayload, feeder.getIp(), "/schedule");
    }

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    public void zero() {
        feederService.findAll().forEach(feeder -> sendData("", feeder.getIp(), "/zero"));
    }


    @PostMapping("/IP")
    @ResponseBody
    public String receiveIP(String address, Long feederId) {
        // Log the received data
        logger.info("IP: {}", address);

        Feeder feeder = feederService.findOrCreateById(feederId);
        feeder.setIp(address);
        feederService.save(feeder);


        LocalTime date = LocalTime.now();
        System.out.println("Date = " + date);
        LocalTime start = date;

//        while (start.getDayOfWeek() != DayOfWeek.MONDAY) {
//            start = start.minusDays(1);
//        }
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
//        long seconds = LocalTime.now().atZone(zoneId).toEpochSecond() - start.atZone(zoneId).toEpochSecond();
        long seconds = start.until(LocalTime.now(), ChronoUnit.SECONDS);
        // Return a response message
        return String.valueOf(seconds);
    }

    public static void feedNow( @RequestParam int amount, Feeder feeder) {

        String requestPayload = String.format("amount=%s", amount);
        sendData(requestPayload, feeder.getIp(), "/feedNow");
    }

    private static void sendData(String payload, String ip, String path) {
        if (ip != null) {
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setContentLength(payload.length());

            // Create the request entity
            HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

            // Set the URL of the API endpoint
            String apiUrl = "http://" + ip + path;

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
                logger.info("Response body: {}", responseBody);
            } else {
                logger.error("POST request failed with status code: {}", responseEntity.getStatusCodeValue());
            }
            logger.info("oi2");
        }

    }
}


