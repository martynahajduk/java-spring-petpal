package be.kdg.programming3.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageProcessingService implements ImageProcessorServiceIntf {

    @Override
    public void saveImageFromBase64(String base64Image, String fileName) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

            String directoryPath = "F:/petpal/graphs";
            Path outputPath = Paths.get(directoryPath, fileName);

            if (!Files.exists(outputPath.getParent())) {
                Files.createDirectories(outputPath.getParent());
                System.out.println("Created directory: " + outputPath.getParent());
            }

            Files.write(outputPath, decodedBytes);
            System.out.println("Image successfully saved at: " + outputPath);

        } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Error saving image " + fileName + ": " + e.getMessage());
    }
        }

    }