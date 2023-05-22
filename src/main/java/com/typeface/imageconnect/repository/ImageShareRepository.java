package com.typeface.imageconnect.repository;

import com.typeface.imageconnect.entity.Image;
import com.typeface.imageconnect.entity.ImageShare;
import com.typeface.imageconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageShareRepository extends JpaRepository<ImageShare, Long> {

    @Query("SELECT i.image FROM ImageShare i WHERE i.receivingUser.username = :username")
    List<Image> findSharedImagesByUsername(@Param("username") String username);

    boolean existsByReceivingUserAndImage(User receivingUser, Image image);

    List<ImageShare> findByImageId(Long imageId);
}

