package ivolapuma.miniautorizador.validator;

import ivolapuma.miniautorizador.validator.exception.ValidatorException;

/**
 * Classe base para todas as subclasses de validação da aplicação.
 *
 * Esta classe implementa o ValidatorStrategy e permite o valor a ser validado e uma mensagem a ser enviada
 * na exceção (do tipo ValidatorException) a ser lançada caso a regra de validação não seja atendida.
 *
 * @param <T> Tipo do valor a ser validado.
 */
abstract public class Validator<T> implements ValidatorStrategy {

    private static final String MESSAGE_DEFAULT = "Invalid value";

    protected T value;
    private String message;

    /**
     * Construtor padrão.
     * Define o tipo da exceção e mensagem default.
     */
    public Validator() {
        this.value = null;
        this.message = MESSAGE_DEFAULT;
    }

    /**
     * Define o valor a ser validado.
     * @param value Valor a ser validado
     * @return
     */
    public Validator<T> value(T value) {
        this.value = value;
        return this;
    }

    /**
     * Define a mensagem a ser lançada em caso da validação falhar.
     * @param message
     * @return
     */
    public Validator<T> message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String getExceptionMessage() {
        return this.message;
    }
}
