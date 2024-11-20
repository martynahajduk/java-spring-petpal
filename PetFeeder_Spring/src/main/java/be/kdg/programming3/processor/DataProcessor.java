package be.kdg.programming3.processor;

import be.kdg.programming3.collector.PetPalData;

//configures the basic "contract" for all the processors of the data
//method which all the processors implements

public abstract class DataProcessor {

    public abstract void saveToDatabase(PetPalData data);

}
