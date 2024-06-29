package ivolapuma.miniautorizador.validator;

/**
 * Classe base para todas as subclasses de validação da aplicação.
 *
 * Esta classe implementa o ValidatorStrategy e define um padrão tipo Builder para definir
 * o valor a ser validado e a exceção e mensagem a ser lançado em caso da validação falhar.
 *
 * @param <T> Tipo do valor a ser validado.
 */
abstract public class Validator<T> implements ValidatorStrategy {

    private static final String MESSAGE_DEFAULT = "Invalid value";

    protected T value;
    private Class<?> exception;
    private String message;

    /**
     * Construtor padrão.
     * Define o tipo da exceção e mensagem default.
     */
    public Validator() {
        this.value = null;
        this.exception = Exception.class;
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
     * Define o tipo da exceção e a mensagem a serem lançados em caso da validação falhar.
     * @param type Tipo da exceção a ser lançada
     * @param message Conteúdo da messagem que será definida na instância da exceção a ser lançada.
     * @return
     * @param <E>
     */
    public <E extends Exception> Validator<T> exception(Class<E> type, String message) {
        this.exception = type;
        this.message = message;
        return this;
    }

    /**
     * Define o tipo da exceção a ser lançada em caso da validação falhar.
     * @param type Tipo da exceção a ser lançada
     * @return
     * @param <E> Tipo que extenda Exception
     */
    public <E extends Exception> Validator<T> exception(Class<E> type) {
        this.exception = type;
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

    /**
     * Implementação do método que lança uma exceção que seja esperada em caso
     * da validação falhar.
     * @throws Throwable
     */
    @Override
    public void throwException() throws Throwable {
        throw (Throwable) exception.getDeclaredConstructor(String.class).newInstance(message);
    }

}
