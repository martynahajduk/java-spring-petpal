package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;

import java.util.ArrayList;
import java.util.List;

public class CompositeProcessor extends DataProcessor {

    private final List<DataProcessor> processors = new ArrayList<>();

    //adding processor to the composite
    public void addProcessor(DataProcessor processor) {
        processors.add(processor);
    }


    //sequentially process data using all processors
    @Override
    public void saveToDatabase(PetPalData data) {
        for (DataProcessor processor : processors) {
            processor.saveToDatabase(data);
        }
    }

    // Check if any processors are added
    public boolean hasProcessors() {
        return !processors.isEmpty();
    }
}
