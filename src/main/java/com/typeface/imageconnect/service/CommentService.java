package com.typeface.imageconnect.service;

import com.typeface.imageconnect.entity.Comment;
import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.entity.User;
import com.typeface.imageconnect.exception.AccessDeniedException;
import com.typeface.imageconnect.repository.CommentRepository;
import com.typeface.imageconnect.repository.ImageRepository;
import com.typeface.imageconnect.repository.ImageShareRepository;
import com.typeface.imageconnect.repository.UserRepository;
import com.typeface.imageconnect.shared.CreateCommentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageShareRepository imageShareRepository;
    private final NotificationService notificationService;


    @Autowired
    public CommentService(CommentRepository commentRepository, ImageRepository imageRepository, UserRepository userRepository,
            ImageShareRepository imageShareRepository, NotificationService notificationService) {
        this.commentRepository = commentRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.imageShareRepository = imageShareRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Comment createComment(CreateCommentRequest request) {
        Image image = imageRepository.findById(request.getImageId())
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + request.getImageId()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + request.getUsername()));
        // Check if the image is shared with the user or the user owned the image.
        boolean result = imageShareRepository.existsByReceivingUserAndImage(user, image);
        log.info("Result {}", result);
        if (!image.getOwner().equals(user) && !imageShareRepository.existsByReceivingUserAndImage(user, image)) {
            throw new AccessDeniedException("Access denied. You are not allowed to comment on this image.");
        }
        Comment comment = new Comment();
        comment.setImage(image);
        comment.setUser(user);
        comment.setText(request.getCommentText());
        Comment savedComment = commentRepository.save(comment);

        // Most Imp for now it is on demand, but we can place it in message queue for asynchronous processing
        this.notificationService.notifyUsers(user, image);
        return savedComment;


    }


    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByImage(Long imageId) {

        return this.commentRepository.findAllByImageIdOrderByUpdatedAtDesc(imageId);
    }


    public void deleteAllRecords() {
        commentRepository.deleteAll();
    }

}

