package com.typeface.imageconnect.repository;

import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByOwner(Optional<User> user);
}
