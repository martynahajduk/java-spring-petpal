package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.PetDataLogService;
import be.kdg.programming3.service.ResearchDataService;
import be.kdg.programming3.service.ResearchDataServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Value("${python.server.url}")
    private String pythonServerURL;

    private final RestTemplate restTemplate = new RestTemplate();
    private final PetDataLogService petDataLogService;
    private final ResearchDataService researchDataService;
    private final ObjectMapper objectMapper;

    public PredictionController(PetDataLogService petDataLogService, ResearchDataService researchDataService, ObjectMapper objectMapper) {
        this.petDataLogService = petDataLogService;
        this.researchDataService = researchDataService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/train")
    public ResponseEntity<List<Map<String, Object>>> trainModel() {
        try {
            String researchDataJson = researchDataService.getResearchDataAsJson();
//            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> researchData = objectMapper.readValue(researchDataJson, List.class);
            return ResponseEntity.ok(researchData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Return null if there's an error
        }
    }

    @PostMapping("/visualize")
    public ResponseEntity<String> visualizeData() {
        try {
            // Fetch real pet data from the database
            List<PetDataLog> petData = petDataLogService.findAll();

            // Transform PetDataLog to the expected structure
            List<Map<String, Object>> transformedRealData = petData.stream()
                    .map(pet -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("age_weeks", pet.getAgeWeeks());
                        map.put("breed", pet.getBreed().name()); // Convert enum to String
                        map.put("sex", pet.getSex());
                        map.put("pet_weight", pet.getPetWeight());
                        map.put("food_intake", pet.getBowlWeight());
                        return map;
                    })
                    .collect(Collectors.toList());

            // Fetch research data dynamically
            String researchDataJson = researchDataService.getResearchDataAsJson();

            // Deserialize research data JSON into a List
//            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> researchData = objectMapper.readValue(researchDataJson, List.class);

            // Create payload to send to Python (include both real and research data)
            Map<String, Object> payload = new HashMap<>();
            payload.put("real_data", transformedRealData);
            payload.put("research_data", researchData);

            // Send payload to Python server
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    pythonServerURL + "/api/visualize", request, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating visualization: " + e.getMessage());
        }
    }

    @PostMapping("/descriptive")
    public ResponseEntity<String> makeDescriptiveGraphs() {
        try {
            // Fetch real data
            List<PetDataLog> petData = petDataLogService.findAll();

            // Serialize real data to JSON using the injected ObjectMapper
            String descriptive = objectMapper.writeValueAsString(petData);

            // HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(descriptive, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    pythonServerURL + "/api/descriptive", request, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            // Handle errors and return appropriate HTTP status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error making descriptive graphs: " + e.getMessage());
        }
    }

}
