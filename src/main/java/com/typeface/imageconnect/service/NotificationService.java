package com.typeface.imageconnect.service;

import com.typeface.imageconnect.entity.Comment;
import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.entity.ImageShare;
import com.typeface.imageconnect.entity.Notification;
import com.typeface.imageconnect.entity.User;
import com.typeface.imageconnect.repository.ImageShareRepository;
import com.typeface.imageconnect.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ImageShareRepository imageShareRepository;



    public void notifyUsers(User commentedUser, Image image) {
        log.info("Notifying the users");
        // if owner has commented no need to notify otherwise we need to notify to user
        String notificationText = commentedUser.getUsername() + " commented on your image " + image.getName();
        if (!image.getOwner().equals(commentedUser)) {
            createNotification(image.getOwner(), image, notificationText);
        }

        List<ImageShare> imageShareList = this.imageShareRepository.findByImageId(image.getId());

        if (imageShareList == null) {
            log.warn("This image was not shared");
        }

        for (ImageShare imageShare: imageShareList) {
            if (!imageShare.getReceivingUser().equals(commentedUser)) {
                createNotification(imageShare.getReceivingUser(), image, notificationText);
            }
        }
        log.info("Done with the notifying");

    }

    public void createNotification(User user,  Image image, String notificationText) {
        Notification notification = new Notification();
        notification.setRecipient(user);
        notification.setNotificationText(notificationText);
        notification.setImage(image);
        notificationRepository.save(notification);
    }

    public void deleteAllRecords() {
        notificationRepository.deleteAll();
    }

}
