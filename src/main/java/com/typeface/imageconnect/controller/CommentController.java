package com.typeface.imageconnect.controller;

import com.typeface.imageconnect.entity.Comment;
import com.typeface.imageconnect.exception.AccessDeniedException;
import com.typeface.imageconnect.service.CommentService;
import com.typeface.imageconnect.shared.CreateCommentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest request) {
        try {
            Comment createdComment = commentService.createComment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @RequestMapping(value = "/create", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptionsRequest() {
        // Return a response with appropriate headers to allow the CORS preflight request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Methods", "POST, OPTIONS");  // Add allowed methods
        headers.add("Access-Control-Allow-Headers", "Content-Type");  // Add allowed headers
        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/{imageId}")
    public List<Comment> getCommentsByImage(@PathVariable Long imageId) {
        log.info("Fetching the comments for the imageId {}", imageId);
        return this.commentService.getCommentsByImage(imageId);
    }

}

