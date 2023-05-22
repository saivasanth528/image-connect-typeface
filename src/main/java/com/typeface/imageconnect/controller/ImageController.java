package com.typeface.imageconnect.controller;

import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.service.ImageService;
import com.typeface.imageconnect.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@Slf4j
public class ImageController {

    private final StorageService storageService;
    private final ImageService imageService;

    @Autowired
    public ImageController(StorageService storageService, ImageService imageService) {
        this.storageService = storageService;
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public Image uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("Received the image to upload {}", file.getName());
        byte[] fileContent = file.getBytes();

        String fileName = file.getOriginalFilename();
        String imageUrl = storageService.uploadImage(fileContent, fileName);

        Image image = new Image();
        image.setMediaUrl(imageUrl);
        // Set other properties of the image

        imageService.saveImage(image);

        return image;
    }

    @GetMapping("/owned/{username}")
    public List<Image> getImagesByUsername(@PathVariable String username) {
        log.info("Fetching the images for username {}", username);
        return imageService.getImagesByUsername(username);
    }







}

