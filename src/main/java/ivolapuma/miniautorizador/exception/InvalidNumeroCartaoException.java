package ivolapuma.miniautorizador.exception;

/**
 * Exceção lançada quando o Número de Cartão informado é inválido para o padrão da aplicação.
 */
public class InvalidNumeroCartaoException extends Exception {

    public InvalidNumeroCartaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
