package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class PredictionController {

    @Value("${python.server.url}")
    private String pythonServerURL;

    private final RestTemplate restTemplate = new RestTemplate();
    private final PetDataLogService petDataLogService;
    private final ImageProcessorServiceIntf imageProcessorServiceIntf;
    private final ResearchDataService researchDataService;

    public PredictionController(PetDataLogService petDataLogService, ResearchDataService researchDataService, ImageProcessorServiceIntf imageProcessorServiceIntf) {
        this.petDataLogService = petDataLogService;
        this.imageProcessorServiceIntf = imageProcessorServiceIntf;
        this.researchDataService = researchDataService;
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

    @GetMapping("/all")
    public String getAllGraphs(Model model) {
        try {
            List<Map<String, Object>> realData = fetchRealData();
            Map<String, Object> payload = Collections.singletonMap("real_data", realData);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    pythonServerURL + "/api/visualize",
                    HttpMethod.POST,
                    new HttpEntity<>(payload),
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> graphsData = response.getBody();

                // Debug вывод для проверки ответа от Python-сервера
                System.out.println("Response from Python Server: " + graphsData);

                // Сохранение графиков
                saveGraph(graphsData, "growth_trend_base64", "./static/plots/growth_trend.png", model, "growthTrendPath");
                saveGraph(graphsData, "food_intake_trend_base64", "./static/plots/food_intake_trend.png", model, "foodIntakeTrendPath");
                saveGraph(graphsData, "scatter_plot_base64", "./static/plots/scatter_plot.png", model, "scatterPlotPath");
                saveGraph(graphsData, "bar_chart_base64", "./static/plots/bar_chart.png", model, "barChartPath");
                saveGraph(graphsData, "histogram_base64", "./static/plots/histogram.png", model, "histogramPath");

                // Добавление заключений
                model.addAttribute("growthTrendConclusion", graphsData.getOrDefault("growth_trend_conclusion", "No conclusion available"));
                model.addAttribute("foodIntakeTrendConclusion", graphsData.getOrDefault("food_intake_trend_conclusion", "No conclusion available"));
                model.addAttribute("scatterPlotConclusion", graphsData.getOrDefault("scatter_plot_conclusion", "No conclusion available"));
                model.addAttribute("barChartConclusion", graphsData.getOrDefault("bar_chart_conclusion", "No conclusion available"));
                model.addAttribute("histogramConclusion", graphsData.getOrDefault("histogram_conclusion", "No conclusion available"));

                // Обработка аномалий
                processAnomalies(graphsData, "growth_anomalies", model, "growthAnomalies");
                processAnomalies(graphsData, "food_anomalies", model, "foodAnomalies");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error while loading graphs: " + e.getMessage());
            return "error";
        }
        return "healthtracker";
    }

    private void processAnomalies(Map<String, Object> graphsData, String key, Model model, String attributeName) {
        List<Map<String, Object>> anomalies = (List<Map<String, Object>>) graphsData.get(key);
        if (anomalies != null) {
            model.addAttribute(attributeName, anomalies);
        } else {
            model.addAttribute(attributeName, Collections.emptyList());
        }
    }


    private void saveGraph(Map<String, Object> graphsData, String base64Key, String fileName, Model model, String modelKey) {
        if (graphsData.containsKey(base64Key)) {
            String base64Image = graphsData.get(base64Key).toString();
            imageProcessorServiceIntf.saveImageFromBase64(base64Image, fileName);
            model.addAttribute(modelKey, "/static/plots/" + fileName); // Correct static path
        }
    }

    private List<Map<String, Object>> fetchRealData() {
        // Fetch all pet data logs
        return petDataLogService.findAll().stream()
                .map(pet -> Map.<String, Object>of(
                        "age_weeks", pet.getAgeWeeks(),
                        "pet_weight", pet.getPetWeight(),
                        "food_intake", pet.getBowlWeight()
                ))
                .collect(Collectors.toList());
    }
}

