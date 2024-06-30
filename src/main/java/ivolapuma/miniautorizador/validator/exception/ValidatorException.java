package ivolapuma.miniautorizador.validator.exception;

/**
 * Exceção lançada pelas classes que implementam ValidatorStrategy quando uma regra de validação não é atentida.
 */
public class ValidatorException extends Exception {

    public ValidatorException(String message) {
        super(message);
    }

}
