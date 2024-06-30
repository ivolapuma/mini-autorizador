package ivolapuma.miniautorizador.exception;

/**
 * Exceção lançada quando alguma requisição contém dados inválidos ou mal-formados.
 */
public class BadRequestException extends Exception {

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
