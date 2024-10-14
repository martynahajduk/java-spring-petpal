package be.kdg.programming3.service;

import be.kdg.programming3.domain.Feeder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FeederService {
    List<Feeder> findAll();
     Feeder save(Feeder feeder);
     void deleteById(Long id);


}
