package com.notifications.consumer;

import com.notifications.config.RabbitMQConfig;
import com.notifications.model.Notification;
import com.notifications.model.NotificationStatus;
import com.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICACOES)
    public void processar(Notification notification) {
        log.info("Processando notificação para: {}", notification.getDestinatario());

        try {
            log.info("E-mail enviado para: {} | Assunto: {}",
                    notification.getDestinatario(),
                    notification.getAssunto());

            notification.setStatus(NotificationStatus.ENVIADA);
            notification.setEnviadoEm(LocalDateTime.now());
        } catch (Exception e) {
            log.error("Erro ao enviar notificação para: {}", e.getMessage());
            notification.setStatus(NotificationStatus.FALHOU);
            notification.setMensagemErro(e.getMessage());
        }

        repository.save(notification);
    }
}
