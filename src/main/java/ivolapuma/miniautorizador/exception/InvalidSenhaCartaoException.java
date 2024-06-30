package ivolapuma.miniautorizador.exception;

/**
 * Exceção lançada quando a Senha do Cartão informada é inválido para o padrão da aplicação.
 */
public class InvalidSenhaCartaoException extends Exception {

    public InvalidSenhaCartaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
