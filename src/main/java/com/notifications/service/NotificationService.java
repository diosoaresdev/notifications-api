package com.notifications.service;

import com.notifications.dto.NotificationRequest;
import com.notifications.model.Notification;
import com.notifications.model.NotificationStatus;
import com.notifications.producer.NotificationProducer;
import com.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationProducer producer;

    public Notification criar(NotificationRequest request) {
        Notification notification = Notification.builder()
                .destinatario(request.getDestinatario())
                .assunto(request.getAssunto())
                .conteudo(request.getConteudo())
                .status(NotificationStatus.PENDENTE)
                .build();

        Notification salva = repository.save(notification);
        producer.enviar(salva);
        return salva;
    }

    public List<Notification> buscarTodos() {
        return repository.findAll();
    }

    public Notification buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacao nao encontrada"));
    }
}
