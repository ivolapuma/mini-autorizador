package ivolapuma.miniautorizador.validator;

/**
 * Interface baseado no Pattern Strategy para definir a estratégia de validação a ser usada na aplicação.
 */
public interface ValidatorStrategy {

    /**
     * Método default para realizar a regra de validação da classe.
     * Lança uma exceção, caso a validação falhe.
     * @throws Throwable
     */
    default void validate() throws Throwable {
        if (!isValid()) {
            throwException();
        }
    }

    /**
     * Método a ser implementado nas subclasses que define a regra de validação da classe.
     * @return
     */
    boolean isValid();

    /**
     * Método a ser implementado nas subclasses que lança uma exceção que seja esperada em caso
     * da validação falhar.
     * @throws Throwable
     */
    void throwException() throws Throwable;

}
