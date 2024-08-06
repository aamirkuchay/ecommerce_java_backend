package com.ecommerce.service.impl;

import com.ecommerce.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

private static final String IMAGE_DIRECTORY = "C:\\usr\\ecommerce\\";
    private static final String IMAGE_BASE_URL = "http://localhost:8085/api/images/";

    public String storeFile(MultipartFile file, String productSlug) {
        try {
            Path uploadPath = Paths.get(IMAGE_DIRECTORY).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String fileName = StringUtils.cleanPath(productSlug + "-" + file.getOriginalFilename());
            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file. Please try again!", ex);
        }
    }

    public byte[] loadFile(String filename) {
        try {
            Path path = Paths.get(IMAGE_DIRECTORY + filename);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file " + filename, e);
        }
    }
}
