package ivolapuma.miniautorizador.exception;

/**
 * Exceção lançada quando o Saldo do Cartão é insuficiente para a Transação a ser realizada.
 */
public class InsufficientSaldoException extends Exception {

    public InsufficientSaldoException(String message, Throwable cause) {
        super(message, cause);
    }
}
