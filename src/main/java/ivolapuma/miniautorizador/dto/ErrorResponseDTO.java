package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe DTO que representa os dados enviados em caso de erro gerenciado pelo ControllerAdvice da aplicação.
 */
public class ErrorResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6727112301144329110L;

    /**
     * Tipo do erro lançado.
     */
    private String error;

    /**
     * Descrição do erro lançado.
     */
    private String message;

    /**
     * Timestamp do acontecimento do erro.
     */
    private LocalDateTime timestamp;

    public ErrorResponseDTO() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
