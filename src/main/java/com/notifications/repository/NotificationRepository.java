package com.notifications.repository;

import com.notifications.model.Notification;
import com.notifications.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByDestinatario(String destinatario);
}
