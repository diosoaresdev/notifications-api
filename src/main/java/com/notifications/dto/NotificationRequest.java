package com.notifications.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotBlank(message = "Destinatário não pode ser vazio")
    @Email(message = "Formato de e-mail inválido")
    private String destinatario;

    @NotBlank(message = "Assunto não pode ser vazio")
    private String assunto;

    @NotBlank(message = "Conteúdo não pode ser vazio")
    private String conteudo;
}
