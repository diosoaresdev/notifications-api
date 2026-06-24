package com.notifications.service;

import com.notifications.dto.NotificationRequest;
import com.notifications.model.Notification;
import com.notifications.model.NotificationStatus;
import com.notifications.producer.NotificationProducer;
import com.notifications.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private NotificationProducer producer;

    @InjectMocks
    private NotificationService service;

    @Test
    @DisplayName("Deve criar e enviar notificação com sucesso")
    void deveCriarEEnviarNotificacaoComSucesso() {
        // Arrange
        NotificationRequest request = new NotificationRequest(
                "teste@gmail.com",
                "Assunto teste",
                "Conteúdo teste"
        );

        Notification notificacaoSalva = Notification.builder()
                .id(1L)
                .destinatario("teste@gmail.com")
                .assunto("Assunto teste")
                .conteudo("Conteúdo teste")
                .status(NotificationStatus.PENDENTE)
                .build();

        when(repository.save(any(Notification.class))).thenReturn(notificacaoSalva);

        // Act
        Notification resultado = service.criar(request);

        // Assert
        assertNotNull(resultado);
        assertEquals("teste@gmail.com", resultado.getDestinatario());
        assertEquals(NotificationStatus.PENDENTE, resultado.getStatus());
        verify(producer, times(1)).enviar(notificacaoSalva);
    }

    @Test
    @DisplayName("Deve listar todas as notificações")
    void deveListarTodasAsNotificacoes() {
        // Arrange
        List<Notification> notificacoes = List.of(
                Notification.builder()
                        .id(1L)
                        .destinatario("teste@gmail.com")
                        .assunto("Assunto 1")
                        .conteudo("Conteúdo 1")
                        .status(NotificationStatus.ENVIADA)
                        .build(),
                Notification.builder()
                        .id(2L)
                        .destinatario("teste2@gmail.com")
                        .assunto("Assunto 2")
                        .conteudo("Conteúdo 2")
                        .status(NotificationStatus.PENDENTE)
                        .build()
        );

        when(repository.findAll()).thenReturn(notificacoes);

        // Act
        List<Notification> resultado = service.buscarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Deve retornar notificação quando ID existe")
    void deveRetornarNotificacaoQuandoIdExiste() {
        // Arrange
        Notification notificacao = Notification.builder()
                .id(1L)
                .destinatario("teste@gmail.com")
                .assunto("Assunto teste")
                .conteudo("Conteúdo teste")
                .status(NotificationStatus.ENVIADA)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(notificacao));

        // Act
        Notification resultado = service.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("teste@gmail.com", resultado.getDestinatario());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID não existe")
    void deveLancarExcecaoQuandoIdNaoExiste() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99L);
        });

        assertEquals("Notificacao nao encontrada", exception.getMessage());
    }
}
