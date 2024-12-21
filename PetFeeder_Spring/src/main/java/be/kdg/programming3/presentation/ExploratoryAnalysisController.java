package be.kdg.programming3.presentation;

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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class ExploratoryAnalysisController {

    @Value("${python.server.url}")
    private String pythonServerURL;

    private final RestTemplate restTemplate = new RestTemplate();
    private final PetDataLogService petDataLogService;
    private final ImageProcessorServiceIntf imageProcessorServiceIntf;
    private final ResearchDataService researchDataService;

    public ExploratoryAnalysisController(PetDataLogService petDataLogService, ResearchDataService researchDataService, ImageProcessorServiceIntf imageProcessorServiceIntf) {
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
            // Fetch real data and send it to Python API
            List<Map<String, Object>> realData = fetchRealData();
            Map<String, Object> payload = Collections.singletonMap("real_data", realData);

            // Call Python API
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    pythonServerURL + "/api/visualize",
                    HttpMethod.POST,
                    new HttpEntity<>(payload),
                    new ParameterizedTypeReference<>() {}
            );

            // Process response
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> graphsData = response.getBody();
                assignAttributes(graphsData, model);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error while loading graphs: " + e.getMessage());
            return "error";
        }
        return "healthtracker";
    }

    private void assignAttributes(Map<String, Object> graphsData, Model model) {
        // Save Graphs
        saveGraph(graphsData, "growth_trend_base64", "growth_trend.png", model, "growthTrendPath");
        saveGraph(graphsData, "food_intake_trend_base64", "food_intake_trend.png", model, "foodIntakeTrendPath");
        saveGraph(graphsData, "scatter_plot_base64", "scatter_plot.png", model, "scatterPlotPath");
        saveGraph(graphsData, "bar_chart_base64", "bar_chart.png", model, "barChartPath");
        saveGraph(graphsData, "histogram_base64", "histogram.png", model, "histogramPath");

        // Add Conclusions
        model.addAttribute("growthTrendConclusion", graphsData.getOrDefault("growth_trend_conclusion", "No conclusion available"));
        model.addAttribute("foodIntakeTrendConclusion", graphsData.getOrDefault("food_intake_trend_conclusion", "No conclusion available"));
        model.addAttribute("scatterPlotConclusion", graphsData.getOrDefault("scatter_plot_conclusion", "No conclusion available"));
        model.addAttribute("barChartConclusion", graphsData.getOrDefault("bar_chart_conclusion", "No conclusion available"));
        model.addAttribute("histogramConclusion", graphsData.getOrDefault("histogram_conclusion", "No conclusion available"));
    }

    private void saveGraph(Map<String, Object> graphsData, String base64Key, String fileName, Model model, String modelKey) {
        if (graphsData.containsKey(base64Key)) {
            String base64Image = graphsData.get(base64Key).toString();
            imageProcessorServiceIntf.saveImageFromBase64(base64Image, fileName);
            model.addAttribute(modelKey, "/graphs/" + fileName);
        }
    }

    private List<Map<String, Object>> fetchRealData() {
        return petDataLogService.findAll().stream()
                .map(pet -> Map.<String, Object>of(
                        "age_weeks", pet.getAgeWeeks(),
                        "pet_weight", pet.getPetWeight(),
                        "food_intake", pet.getBowlWeight()
                ))
                .collect(Collectors.toList());
    }
}
