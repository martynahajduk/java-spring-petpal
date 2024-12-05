package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.PetDataLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Value("${python.server.url}")
    private String pythonServerURL;

    private final RestTemplate restTemplate = new RestTemplate();
    private final PetDataLogService petDataLogService;

    public PredictionController(PetDataLogService petDataLogService) {
        this.petDataLogService = petDataLogService;
    }

    @PostMapping("/train")
    public ResponseEntity<String> trainModel() throws JsonProcessingException {
        List<PetDataLog> researchData = petDataLogService.findAll();
        String jsonData = new ObjectMapper().writeValueAsString(researchData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL + "/train", request, String.class);

        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/predict")
    public ResponseEntity<String> makePredictions() throws JsonProcessingException {
        List<PetDataLog> petData = petDataLogService.findAll();
        String jsonData = new ObjectMapper().writeValueAsString(petData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServerURL + "/predict", request, String.class);

        return ResponseEntity.ok(response.getBody());
    }
}
