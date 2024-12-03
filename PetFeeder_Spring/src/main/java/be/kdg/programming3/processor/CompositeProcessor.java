package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;
import be.kdg.programming3.service.PetService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompositeProcessor extends DataProcessor {

    private final List<DataProcessor> processors = new ArrayList<>();

    //constructor based dependency injection
    public CompositeProcessor(List<DataProcessor> processors) {
        this.processors.addAll(processors);
    }

    //adding processor to the composite
   // public void addProcessor(DataProcessor processor) {
      //  processors.add(processor);
   // }

    //sequentially process data using all processors
    @Override
    public void saveToDatabase(PetPalData data, PetService petService) {
        for (DataProcessor processor : processors) {
            processor.saveToDatabase(data, petService);
        }
    }

    // Check if any processors are added
    public boolean hasProcessors() {
        return !processors.isEmpty();
    }
}
