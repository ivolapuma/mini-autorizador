package ivolapuma.miniautorizador.exception;

/**
 * Exceção lançada quando alguma regra não permite o processamento da entidade no sistema.
 */
public class UnprocessableEntityException extends Exception {

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
