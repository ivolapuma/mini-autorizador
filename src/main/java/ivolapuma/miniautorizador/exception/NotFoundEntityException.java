package ivolapuma.miniautorizador.exception;

/**
 * Exceção lançada quando a entidade buscada não é localizada no repositório da aplicação.
 */
public class NotFoundEntityException extends Exception {

    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
