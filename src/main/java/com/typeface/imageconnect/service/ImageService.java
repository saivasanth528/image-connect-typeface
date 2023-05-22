package com.typeface.imageconnect.service;

import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.entity.User;
import com.typeface.imageconnect.repository.ImageRepository;
import com.typeface.imageconnect.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class ImageService {


    @Value("${kaggle.dataset.url}")
    private  String url;

    @Value("${image.batch.size}")
    private int batchSize;

    @Value("${no.of.users}")
    private int noOfUsers;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }


    public void uploadImagesToS3AndAssignOwners() {
        try {

            // Extract the images from the zip file to a directory
            String imagesDirectory = extractImagesFromZip();
            log.info("Images Directory {}", imagesDirectory);
            // Read the captions file
            String captionsFilePath = "src/main/resources/captions.txt";
            Map<String, String> imageCaptions = readCaptionsFile(captionsFilePath);
            List<User> users = this.userService.getUsers();
            Collections.sort(users, Comparator.comparingLong(User::getId));
            assignOwnersToImages(imagesDirectory, imageCaptions, users);
        } catch (Exception e) {
            log.error("Exception occurred while uploading images to S3 ", e);
        }
    }


    private void assignOwnersToImages(String imagesDirectory, Map<String, String> imageCaptions, List<User> users)
            throws IOException {
        File[] imageFiles = getFilesInDirectory(imagesDirectory);


        int userIndex = 0;

        for (int i = 0; i < imageFiles.length && userIndex < noOfUsers; i += batchSize) { // since we are creating only for 100 users
            int endIndex = Math.min(i + batchSize, imageFiles.length);
            File[] batch = Arrays.copyOfRange(imageFiles, i, endIndex);
            User user = users.get(userIndex);
            userIndex++;
            processImageBatch(batch, imageCaptions, user);
        }
        log.info("Assigning the images for {} users was done", noOfUsers);
    }

    private void processImageBatch(File[] batch, Map<String, String> imageCaptions, User user) throws IOException {
        for (File file : batch) {
            // Upload the image to S3 and get the image URL
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String imageUrl = storageService.uploadImage(fileContent, file.getName());

            // Get the caption for the image
            String fileName = file.getName();
            String caption = imageCaptions.getOrDefault(fileName, "");

            // Create the Image entity
            Image image = new Image();
            image.setMediaUrl(imageUrl);
            image.setCaption(caption);
            image.setOwner(user);
            image.setName(fileName);
            // Save the image in the database
            saveImage(image);
        }
        log.info("Processed the images for owner {}", user.getUsername());
    }


    private String extractImagesFromZip() throws IOException {
        String imagesDirectory = "src/main/resources/Images";
        String captionsFilePath = "src/main/resources/captions.txt";
        File imagesDir = new File(imagesDirectory);
        if (imagesDir.exists()) {
            imagesDir.deleteOnExit();
        }
        imagesDir.mkdirs();

        File zipFile = new File("src/main/resources/images.zip");

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
            byte[] buffer = new byte[1024];
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    String fileName = new File(zipEntry.getName()).getName();
                    if (fileName.equals("captions.txt")) {
                        File captionsFile = new File(captionsFilePath);
                        try (FileOutputStream outputStream = new FileOutputStream(captionsFile)) {
                            int length;
                            while ((length = zipInputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                        }
                        log.info("Extracted the captions file");
                    } else {
                        String filePath = imagesDirectory + File.separator + fileName;
                        File imageFile = new File(filePath);
                        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
                            int length;
                            while ((length = zipInputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                        }
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
        log.info("Image extraction was done");
        return imagesDirectory;
    }


    private Map<String, String> readCaptionsFile(String filePath) throws IOException {
        Map<String, String> captionsMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String imageName = parts[0].trim();
                    String caption = parts[1].trim();
                    captionsMap.put(imageName, caption);
                }
            }
        }

        return captionsMap;
    }

    /**
     * This function will preserve the order of the images directory
     * @param directoryPath
     * @return File[]
     */

    private File[] getFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles(File::isFile);

        if (files != null) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        }

        return files;
    }


    public List<Image> getImagesByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        return imageRepository.findByOwner(user);
    }

    public List<Image> getImagesByOwner(User user) {
        return imageRepository.findByOwner(Optional.ofNullable(user));
    }

    public void deleteAllRecords() {
        imageRepository.deleteAll();
    }
}
