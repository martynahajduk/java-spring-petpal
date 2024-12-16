package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.ui.Model;
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
    private final ImageProcessorServiceIntf imageProcessorServiceIntf;

    public PredictionController(PetDataLogService petDataLogService, ResearchDataService researchDataService, ObjectMapper objectMapper, ImageProcessorServiceIntf imageProcessorServiceIntf) {
        this.petDataLogService = petDataLogService;
        this.researchDataService = researchDataService;
        this.objectMapper = objectMapper;
        this.imageProcessorServiceIntf = imageProcessorServiceIntf;
    }

    @PostMapping("/train")
    public ResponseEntity<List<Map<String, Object>>> trainModel() {
        try {
            String researchDataJson = researchDataService.getResearchDataAsJson();
           ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> researchData = objectMapper.readValue(researchDataJson, List.class);
            return ResponseEntity.ok(researchData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Return null if there's an error
        }
    }

    @PostMapping("/visualize")
    public ResponseEntity<Map<String, Object>> visualizeData() {
        try {
            // Fetch real pet data from the database
            List<PetDataLog> petData = petDataLogService.findAll();

            // Transform PetDataLog to include only numeric fields
            List<Map<String, Object>> transformedRealData = petData.stream()
                    .map(pet -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("age_weeks", pet.getAgeWeeks());
                        map.put("pet_weight", pet.getPetWeight());
                        map.put("food_intake", pet.getBowlWeight());
                        return map;
                    })
                    .collect(Collectors.toList());

            // Create payload to send to Flask
            Map<String, Object> payload = new HashMap<>();
            payload.put("real_data", transformedRealData);

            // Send payload to Flask server
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    pythonServerURL + "/api/visualize", request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                // Return the response body as JSON
                return ResponseEntity.ok(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Error from Python server."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error generating visualization: " + e.getMessage()));
        }
    }



//    @PostMapping("/descriptive")
//    public ResponseEntity<String> makeDescriptiveGraphs() {
//        try {
//            // Fetch real data
//            List<PetDataLog> petData = petDataLogService.findAll();
//
//            // Serialize real data to JSON using the injected ObjectMapper
//            String descriptive = objectMapper.writeValueAsString(petData);
//
//            // HTTP request
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> request = new HttpEntity<>(descriptive, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(
//                    pythonServerURL + "/api/descriptive", request, String.class);
//
//            return ResponseEntity.ok(response.getBody());
//        } catch (Exception e) {
//            // Handle errors and return appropriate HTTP status
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error making descriptive graphs: " + e.getMessage());
//        }
//    }

}
