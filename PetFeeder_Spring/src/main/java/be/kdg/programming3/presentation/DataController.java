package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.PetDataLog;

import be.kdg.programming3.service.PetDataLogService;
import be.kdg.programming3.service.PetDataLogServiceS;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;



@RestController
public class DataController {

    private final PetDataLogService petDataLogService;
    private final String pythonServerURL = "http://localhost:5000/receiveData"; // Python server endpoint

    @Autowired
    public DataController(PetDataLogService petDataLogService) {
        this.petDataLogService = petDataLogService;
    }

    /*@GetMapping("/api/sendData")
    public ResponseEntity<String> sendData() {
        try {
            // Fetch data from the service layer
            List<PetDataLog> petDataLogs = petDataLogService.getAllLogs();

            // Convert the data to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(petDataLogs);

            // Send data to Python server using RestTemplate (GET with query parameter)
            RestTemplate restTemplate = new RestTemplate();
            String url = pythonServerURL + "?data=" + json; // Append JSON as query parameter
            String response = restTemplate.getForObject(url, String.class);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to send data");
        }
    }

     */
    @PostMapping("/api/sendData")
    public ResponseEntity<String> sendDataViaPost() {
        try {
            // Create a sample list of PetDataLog objects
            List<PetDataLog> petDataLogs = List.of(
                    new PetDataLog(1L, 2, Breed.CHINESE, 10.0, 0.5, 0.3)
            );

            // Convert to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(petDataLogs);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request
            HttpEntity<String> request = new HttpEntity<>(json, headers);

            // Send POST request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL, request, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to send data via POST");
        }
    }
}