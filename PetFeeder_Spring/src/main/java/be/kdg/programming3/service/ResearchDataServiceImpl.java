package be.kdg.programming3.service;


import be.kdg.programming3.repository.ResearchDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResearchDataServiceImpl implements ResearchDataService {

    private final ResearchDataRepository researchDataRepository;

    @Autowired
    public ResearchDataServiceImpl(ResearchDataRepository researchDataRepository) {
        this.researchDataRepository = researchDataRepository;
    }

    @Override
    public String getResearchDataAsJson() {
        try {
            List<Map<String, Object>> researchData = researchDataRepository.findAllResearchData();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(researchData);
        }catch (Exception e) {
            throw new RuntimeException("Failed to fetch or serialize research data: " + e.getMessage());
        }
    }

}



