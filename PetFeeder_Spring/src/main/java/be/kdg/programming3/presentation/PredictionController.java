package be.kdg.programming3.presentation;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.domain.PetDataLog;
import be.kdg.programming3.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class PredictionController {
    private static final Logger logger = LoggerFactory.getLogger(PredictionController.class);

    @Value("${python.server.url}")
    private String pythonServerURL;

    private final FeederService feederService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final PetDataLogService petDataLogService;
    private final ImageProcessorServiceIntf imageProcessorServiceIntf;
    private final ResearchDataService researchDataService;

    public PredictionController(FeederService feederService, PetDataLogService petDataLogService, ResearchDataService researchDataService, ImageProcessorServiceIntf imageProcessorServiceIntf) {
        this.feederService = feederService;
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
            Map<Long, List<PetDataLog>> petDataMap = new HashMap<>();
            for (Feeder feeder : feederService.findAll()) {
                petDataMap.put(feeder.getId(), petDataLogService.findAllByFeederId(feeder.getId()));
            }

            Map<Long, List<Map<String, Object>>> transformedRealDataMap = new HashMap<>();
            for (Map.Entry<Long, List<PetDataLog>> entry : petDataMap.entrySet()) {
                transformedRealDataMap.put(entry.getKey(), entry.getValue().stream().map(pet -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("age_weeks", pet.getAgeWeeks());
                    map.put("pet_weight", pet.getPetWeight());
                    map.put("breed", pet.getBreed());
                    map.put("food_intake", pet.getBowlWeight()); // Rename bowl_weight -> food_intake
                    map.put("sex", pet.getSex());
                    return map;
                }).toList());
            }
            logger.debug("{}", transformedRealDataMap);
            List<Map<String, Object>> realData = fetchRealData();
            logger.debug("{}", realData);
//            Map<String, Object> payload = Collections.singletonMap("real_data", realData);
            Map<String, Object> payload = Collections.singletonMap("real_data", transformedRealDataMap);
            logger.debug("{}", payload);

            logger.debug("oi");
            ResponseEntity<Map<String, Map<String, Object>>> response = restTemplate.exchange(
                    pythonServerURL + "/api/visualize",
                    HttpMethod.POST,
                    new HttpEntity<>(payload),
                    new ParameterizedTypeReference<>() {}
            );
            logger.debug("oii");
            logger.debug("response from Python Server: {}", response);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Map<String, Object>> graphsDataMap = response.getBody();

                // Debug
                logger.debug("graphsData from Python Server: {}", graphsDataMap);

                List<Map<String, Object>> dataList = new ArrayList<>();

                for (Map.Entry<String, Map<String, Object>> entry : graphsDataMap.entrySet()) {
                    Map<String, Object> graphData = entry.getValue();
                    logger.debug(graphData.toString());

//                    saveGraph(graphData, "growth_trend_base64", "/plots/growth_trend"+entry.getKey()+".png", model, "growthTrendPath"+entry.getKey());
//                    saveGraph(graphData, "food_intake_trend_base64", "/plots/food_intake_trend"+entry.getKey()+".png", model, "foodIntakeTrendPath"+entry.getKey());
//                    saveGraph(graphData, "scatter_plot_base64", "/plots/scatter_plot"+entry.getKey()+".png", model, "scatterPlotPath"+entry.getKey());
//                    saveGraph(graphData, "bar_chart_base64", "/plots/bar_chart"+entry.getKey()+".png", model, "barChartPath"+entry.getKey());
//                    saveGraph(graphData, "histogram_base64", "/plots/histogram"+entry.getKey()+".png", model, "histogramPath"+entry.getKey());
//
//                    model.addAttribute("growthTrendConclusion"+entry.getKey(), graphData.getOrDefault("growth_trend_conclusion", "No conclusion available"));
//                    model.addAttribute("foodIntakeTrendConclusion"+entry.getKey(), graphData.getOrDefault("food_intake_trend_conclusion", "No conclusion available"));
//                    model.addAttribute("scatterPlotConclusion"+entry.getKey(), graphData.getOrDefault("scatter_plot_conclusion", "No conclusion available"));
//                    model.addAttribute("barChartConclusion"+entry.getKey(), graphData.getOrDefault("bar_chart_conclusion", "No conclusion available"));
//                    model.addAttribute("histogramConclusion"+entry.getKey(), graphData.getOrDefault("histogram_conclusion", "No conclusion available"));
//
//                    processAnomalies(graphData, "growth_anomalies", model, "growthAnomalies"+entry.getKey());
//                    processAnomalies(graphData, "food_anomalies", model, "foodAnomalies"+entry.getKey());

                    Map<String, Object> dataMap = new HashMap<>();

                    dataMap.put("growthTrendPath", graphData.getOrDefault("growth_trend_base64", ""));
                    dataMap.put("foodIntakeTrendPath", graphData.getOrDefault("food_intake_trend_base64", ""));
                    dataMap.put("scatterPlotPath", graphData.getOrDefault("scatter_plot_base64", ""));
                    dataMap.put("barChartPath", graphData.getOrDefault("bar_chart_base64", ""));
                    dataMap.put("histogramPath", graphData.getOrDefault("histogram_base64", ""));

                    dataMap.put("growthTrendConclusion", graphData.getOrDefault("growth_trend_conclusion", "No conclusion available"));
                    dataMap.put("foodIntakeTrendConclusion", graphData.getOrDefault("food_intake_trend_conclusion", "No conclusion available"));
                    dataMap.put("scatterPlotConclusion", graphData.getOrDefault("scatter_plot_conclusion", "No conclusion available"));
                    dataMap.put("barChartConclusion", graphData.getOrDefault("bar_chart_conclusion", "No conclusion available"));
                    dataMap.put("histogramConclusion", graphData.getOrDefault("histogram_conclusion", "No conclusion available"));

                    dataMap.put("growthAnomalies", graphData.getOrDefault("growth_anomalies", ""));
                    dataMap.put("foodAnomalies", graphData.getOrDefault("food_anomalies", ""));

                    dataList.add(dataMap);
                }
            model.addAttribute("dataList", dataList);
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
            Object base64Image = graphsData.get(base64Key);
            if (base64Image != null) {
                imageProcessorServiceIntf.saveImageFromBase64(base64Image.toString(), "/static"+fileName);
                model.addAttribute(modelKey, fileName); // Correct static path
            }
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

