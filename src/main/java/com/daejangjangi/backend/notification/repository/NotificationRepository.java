package com.daejangjangi.backend.notification.repository;

import com.daejangjangi.backend.notification.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
