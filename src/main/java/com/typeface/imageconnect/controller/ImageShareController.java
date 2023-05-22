package com.typeface.imageconnect.controller;

import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.service.ImageShareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/image-share")
public class ImageShareController {

    private final ImageShareService imageShareService;

    public ImageShareController(ImageShareService imageShareService) {
        this.imageShareService = imageShareService;
    }

    @GetMapping("/shared/{username}")
    public ResponseEntity<List<Image>> getSharedImages(@PathVariable String username) {

        List<Image> sharedImages = imageShareService.getSharedImages(username);

        if (sharedImages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sharedImages);
    }
}

