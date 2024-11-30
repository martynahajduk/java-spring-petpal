package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Breed;
import be.kdg.programming3.domain.PetDataLog;

import be.kdg.programming3.service.PetDataLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@RestController
public class DataController {

    private final JdbcTemplate jdbcTemplate;
    private final String pythonServerURL = "http://localhost:5000/receiveData"; // Python server endpoint

    @Autowired
    public DataController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

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
    public ResponseEntity<String> sendDataToPython() {
        try {
            // Query the static hamster_data table from prediction_data schema
            String query = "SELECT * FROM prediction_data.hamster_data";
            List<Map<String, Object>> hamsterDataList = jdbcTemplate.queryForList(query);

            // Convert the query result (List of Maps) into JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(hamsterDataList);

            // Prepare the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare the request with JSON payload
            HttpEntity<String> request = new HttpEntity<>(json, headers);

            // Send the data to the Python server using RestTemplate
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL, request, String.class);

            // Return the response from the Python server
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to send data to Python server");
        }
    }
}