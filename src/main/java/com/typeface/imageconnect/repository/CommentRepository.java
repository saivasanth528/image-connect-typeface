package com.typeface.imageconnect.repository;

import com.typeface.imageconnect.entity.Comment;
import com.typeface.imageconnect.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByImageIdOrderByUpdatedAtDesc(Long imageId);

}
