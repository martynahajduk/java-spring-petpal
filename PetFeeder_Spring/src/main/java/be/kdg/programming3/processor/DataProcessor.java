package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.service.PetService;
import org.springframework.stereotype.Component;

//configures the basic "contract" for all the processors of the data
//method which all the processors implements

@Component
public abstract class DataProcessor {

    public abstract void saveToDatabase(PetPalData data, PetService petService);



}
