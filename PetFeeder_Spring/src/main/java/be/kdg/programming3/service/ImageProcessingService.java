package be.kdg.programming3.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageProcessingService implements ImageProcessorServiceIntf
{

    @Override
    public void saveImageFromBase64(String base64Image, String fileName) {
        try {
            // Decode Base64 string into bytes
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

            // Define the directory and file path
            Path outputPath = Paths.get("src/main/resources/plots", fileName);

            // Ensure the directory exists
            Files.createDirectories(outputPath.getParent());

            // Write the file
            Files.write(outputPath, decodedBytes);

            System.out.println("Image saved as: " + outputPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving image " + fileName + ": " + e.getMessage());
        }
    }
}
