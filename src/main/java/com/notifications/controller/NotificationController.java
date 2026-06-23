package com.notifications.controller;

import com.notifications.dto.NotificationRequest;
import com.notifications.dto.NotificationResponse;
import com.notifications.model.Notification;
import com.notifications.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "Endpoints para envio de notificações")
public class NotificationController {

    private final NotificationService service;

    @Operation(summary = "Cria e envia uma notificação para a fila")
    @PostMapping
    public ResponseEntity<NotificationResponse> criar(@RequestBody @Valid NotificationRequest request) {
        Notification notification = service.criar(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(toResponse(notification));
    }

    @Operation(summary = "Lista todas as notificações")
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> listarTodas() {
        List<NotificationResponse> notifications = service.buscarTodos()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Busca uma notificação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> buscarPorId(@PathVariable Long id) {
        Notification notification = service.buscarPorId(id);
        return ResponseEntity.ok(toResponse(notification));
    }

    private NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .destinatario(notification.getDestinatario())
                .assunto(notification.getAssunto())
                .conteudo(notification.getConteudo())
                .status(notification.getStatus().name())
                .mensagemErro(notification.getMensagemErro())
                .criadoEm(notification.getCriadoEm())
                .enviadoEm(notification.getEnviadoEm())
                .build();
    }
}
