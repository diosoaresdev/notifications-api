package com.notifications.producer;

import com.notifications.config.RabbitMQConfig;
import com.notifications.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviar(Notification notificacao) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NOTIFICACOES,
                RabbitMQConfig.ROUTING_KEY_NOTIFICACOES,
                notificacao
        );
    }
}
