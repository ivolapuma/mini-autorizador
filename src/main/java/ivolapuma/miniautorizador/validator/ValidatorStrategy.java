package ivolapuma.miniautorizador.validator;

import ivolapuma.miniautorizador.validator.exception.ValidatorException;

/**
 * Interface baseado no Pattern Strategy para definir a estratégia de validação a ser usada na aplicação.
 */
public interface ValidatorStrategy {

    /**
     * Método default para realizar a regra de validação da classe.
     * Lança uma exceção, caso a validação falhe.
     * @throws ValidatorException
     */
    default void validate() throws ValidatorException {
        if (!isValid()) {
            throw new ValidatorException(getExceptionMessage());
        }
    }

    /**
     * Método a ser implementado nas subclasses que define a regra de validação da classe.
     * @return
     */
    boolean isValid();

    /**
     * Retorna a mensagem a ser enviada com a exceção lancada.
     * @return
     */
    String getExceptionMessage();

}
