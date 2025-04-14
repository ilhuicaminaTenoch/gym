package com.tlalocalli.gym.util;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtils {

    // Esta propiedad se inyecta desde application.properties
    @Value("${app.upload.directory}")
    private String uploadDirectory;

    /**
     * Valida que la imagen (en forma de bytes) sea JPEG o PNG y que no exceda 5 MB.
     */
    public void validateImage(byte[] imageBytes) {
        Tika tika = new Tika();
        String mimeType = tika.detect(imageBytes);
        if (!(mimeType.equals("image/jpeg") || mimeType.equals("image/png"))) {
            throw new IllegalArgumentException("La imagen debe ser en formato JPEG o PNG. Se detectó: " + mimeType);
        }
        if (imageBytes.length > 5 * 1024 * 1024) { // 5 MB
            throw new IllegalArgumentException("La imagen no debe exceder 5 MB");
        }
    }

    /**
     * Guarda la imagen recibida como MultipartFile en la ruta configurada y retorna un nombre único.
     */
    public String saveImage(MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            // Validar la imagen
            validateImage(imageBytes);
            Tika tika = new Tika();
            String mimeType = tika.detect(imageBytes);
            String extension;
            if ("image/jpeg".equals(mimeType)) {
                extension = "jpg";
            } else if ("image/png".equals(mimeType)) {
                extension = "png";
            } else {
                throw new IllegalArgumentException("Tipo MIME no permitido: " + mimeType);
            }

            // Generar un nombre único utilizando UUID
            String uniqueName = UUID.randomUUID().toString() + "." + extension;
            Path uploadDirPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath);
            }
            Path filePath = uploadDirPath.resolve(uniqueName);
            Files.write(filePath, imageBytes);
            return uniqueName;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
}
