package com.typeface.imageconnect.repository;

import com.typeface.imageconnect.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
