package be.kdg.programming3.service;

import be.kdg.programming3.domain.Feeder;
import be.kdg.programming3.repository.PetRepositrory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class FeederServiceImpl implements FeederService {
    private Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);
    private PetRepositrory petRepositrory;

    public FeederServiceImpl(PetRepositrory petRepositrory) {
        logger.info("Creating PetRepository");
        this.petRepositrory = petRepositrory;
    }

    @Override
    public List<Feeder> getAllFeeders() {
        logger.info("Getting all feeders");
        return petRepositrory.getFeeders();
    }

    @Override
    public Feeder addFeeder(Long id, double reservoirLevel, LocalTime nextFeedingTime, LocalDate nextFeedingDate, LocalDate emptyIN) {
        logger.info("Adding feeder");
        return petRepositrory.creatingFeeder(new Feeder(id, reservoirLevel, nextFeedingTime, nextFeedingDate, emptyIN));
    }

}
