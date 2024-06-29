package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6727112301144329110L;

    private String error;
    private String message;
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
