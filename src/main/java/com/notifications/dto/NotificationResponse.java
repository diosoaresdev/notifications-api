package com.notifications.dto;

import com.notifications.model.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;
    private String destinatario;
    private String assunto;
    private String conteudo;
    private String status;
    private String mensagemErro;
    private LocalDateTime criadoEm;
    private LocalDateTime enviadoEm;
}
