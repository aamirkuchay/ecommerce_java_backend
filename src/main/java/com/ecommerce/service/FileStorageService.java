package com.ecommerce.service;

import com.ecommerce.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
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

//    @Value("${app.upload.dir:${user.home}}")
//    private String uploadDir;
private static final String IMAGE_DIRECTORY = "C:\\usr\\ecommerce\\";

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
}
