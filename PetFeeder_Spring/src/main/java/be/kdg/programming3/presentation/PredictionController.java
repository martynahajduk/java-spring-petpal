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
import java.util.List;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Value("${python.server.url}")
    private String pythonServerURL;

    private final RestTemplate restTemplate = new RestTemplate();
    private final PetDataLogService petDataLogService;
    private final ResearchDataService researchDataService;

    public PredictionController(PetDataLogService petDataLogService, ResearchDataServiceImpl researchDataService) {
        this.petDataLogService = petDataLogService;
        this.researchDataService = researchDataService;
    }

    @PostMapping("/train")
    public ResponseEntity<String> trainModel()  {
        try {
        String researchDataJson = researchDataService.getResearchDataAsJson();

        // Send research data to Python server for training
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(researchDataJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL + "api/train", request, String.class);

        return ResponseEntity.ok(response.getBody());
    } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error training model: " + e.getMessage());
        }
        }

    @PostMapping("/predict")
    public ResponseEntity<String> makePredictions() {
        try {
            // Step 1: Fetch real data
            List<PetDataLog> petData = petDataLogService.findAll();

            // Step 2: Serialize real data to JSON
        String realDataJson = new ObjectMapper().writeValueAsString(petData);

            // Step 3: Prepare HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(realDataJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL + "api/predict", request, String.class);

        return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            // Handle errors and return appropriate HTTP status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making predictions: " + e.getMessage());
        }
    }

    @PostMapping("/descriptive")
    public ResponseEntity<String> makeDescriptiveGraphs() {
        try {
            // Step 1: Fetch real data
            List<PetDataLog> petData = petDataLogService.findAll();

            // Step 2: Serialize real data to JSON
            String realDataJson = new ObjectMapper().writeValueAsString(petData);

            // Step 3: Prepare HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(realDataJson, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL + "api/descriptive", request, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            // Handle errors and return appropriate HTTP status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error making descriptive graphs: " + e.getMessage());
        }
    }
}
