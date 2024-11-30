package be.kdg.programming3.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {

    private final JdbcTemplate jdbcTemplate;

    @Value("${python.server.url}")
    private String pythonServerURL;

    public DataController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint 1: Send Real Data
    @PostMapping("/sendRealData")
    public ResponseEntity<String> sendRealData(@RequestBody Map<String, Object> realData) {
        return sendDataToPythonServer(realData);
    }

    // Endpoint 2: Send Research Data
    @PostMapping("/sendResearchData")
    public ResponseEntity<String> sendResearchData() {
        try {
            // Query the hamster_data table
            String query = "SELECT * FROM prediction_data.hamster_data";
            List<Map<String, Object>> researchData = jdbcTemplate.queryForList(query);

            // Convert data to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(researchData);

            // Send data to Python server
            return sendDataToPythonServer(json);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to send research data: " + e.getMessage());
        }
    }

    // Helper: Send data to Python server
    private ResponseEntity<String> sendDataToPythonServer(Object data) {
        try {
            // Convert data to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(data);

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Send POST request to Python server
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL, request, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to send data to Python server: " + e.getMessage());
        }
    }
}
