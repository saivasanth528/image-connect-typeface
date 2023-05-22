package com.typeface.imageconnect.service;

import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.entity.ImageShare;
import com.typeface.imageconnect.entity.User;
import com.typeface.imageconnect.repository.ImageShareRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ImageShareService {

    private final UserService userService;
    private final ImageService imageService;

    private final ImageShareRepository imageShareRepository;
    @Value("${no.of.users}")
    private int totalUsers;

    @Value("${no.of.images.to.share}")
    private int imagesToShare;
    @Autowired
    public ImageShareService(UserService userService, ImageService imageService ,
        ImageShareRepository imageShareRepository) {
        this.imageService = imageService;
        this.userService = userService;
        this.imageShareRepository = imageShareRepository;
    }

    /**
     * Here the logic is each user shares 8 images with next 10 users.
     * user1 shares 8 images with user2 to user 11
     * user 80 images, each 10 images are shared with every user.
     * Since we have notion of 80 images this is hardcoded like that can be modified to generic version as well
     */
    public void shareImages() {
        log.info("**********Started the Image sharing *************");

        try {
            List<User> users = this.userService.getUsers();

            for (int i = 0; i < totalUsers; i++) {
                User sharingUser = users.get(i);

                int userStartIndex = (i + 1) % totalUsers;
                List<Image> ownedImages = imageService.getImagesByOwner(sharingUser);
                if (ownedImages.size() == 0) {
                    log.error("Owned Images size is zero for {}", sharingUser.toString());
                }
                // Share 80 images with each of the next 10 users

                for (int j = 0; j < 10; j++) {
                    User receivingUser = users.get((userStartIndex + j) % totalUsers);
                    for (int k = 0; k < ownedImages.size(); k++) {
                        Image selectedImage = ownedImages.get(k);
                        ImageShare imageShare = new ImageShare();
                        imageShare.setSharingUser(sharingUser);
                        imageShare.setReceivingUser(receivingUser);
                        imageShare.setImage(selectedImage);
                        this.imageShareRepository.save(imageShare);

                    }
                }
            }
            log.info("**********Completed the Images sharing *************");
        } catch (Exception e) {
            log.error("Exception occurred ", e);
        }



    }

    public List<Image> getSharedImages(String username) {
        return imageShareRepository.findSharedImagesByUsername(username);
    }

    public void deleteAllRecords() {
        imageShareRepository.deleteAll();
    }

}
