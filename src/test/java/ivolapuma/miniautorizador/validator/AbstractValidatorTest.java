package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe abstrata para servir de base para os testes das classes que herdam de Validator.
 *
 * Ao criar uma subclasse, basta sobrescrever o método "public void setup()", definindo os valores
 * dos atributos protected, não esquecendo de anotar o método com @BeforeEach.
 *
 * Execute os testes da subclasse para conferir o resultado.
 *
 * @param <T> Tipo validado pela classe Validator
 * @param <E> Tipo da exceção que deve ser lançada nos casos em que uma Exception é definida para ser
 *           lançada em caso de valor inválido.
 */
abstract public class AbstractValidatorTest<T, E extends Exception> {

    private static final String INVALID_VALUE = "Invalid value";

    /**
     * Deve conter a instância da classe que será testada.
     */
    protected Validator<T> instance;

    /**
     * Deve conter um valor válido para a instância da classe que será testada.
     */
    protected T validValue;

    /**
     * Deve conter um valor inválido para a instância da classe que será testada.
     */
    protected T invalidValue;

    /**
     * Deve conter o tipo da exceção que deve ser lançada nos casos em que uma Exception é
     * definida para ser lançada em caso de valor inválido.
     */
    protected Class<E> exception;

    /**
     * Deve conter a mensagem a ser lançada com a exceção nos casos em que uma Exception é
     * definida para ser lançada em caso de valor inválido.
     */
    protected String message;

    /**
     * Sugerimos definir este método na classe concreta para definir os valores dos atributos:
     * instance
     * validValue
     * invalidValue
     * exception
     * messagem
     *
     * Este método deve ser anotado com @BeforeEach
     */
    @BeforeEach
    abstract public void setup();

    @Test
    public void isValid_withValidValue_shouldReturnTrue() {
        Assertions.assertTrue(instance.value(validValue).isValid());
    }

    @Test
    public void isValid_withInvalidValue_shouldReturnFalse() {
        Assertions.assertFalse(instance.value(invalidValue).isValid());
    }

    @Test
    public void validate_withValidValue_shouldNotThrowException() throws Exception {
        Assertions.assertDoesNotThrow(
                ()->instance.value(validValue).validate()
        );
    }

    @Test
    public void validate_withInvalidValue_shouldThrowExceptionDefault() {
        Exception ex = Assertions.assertThrows(
                Exception.class,
                () -> instance.value(invalidValue).validate()
        );
        Assertions.assertEquals(INVALID_VALUE, ex.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withInvalidValueAndException_shouldThrowExceptionAndMessageDefault() {
        Exception ex = Assertions.assertThrows(
                Exception.class,
                () -> instance.value(invalidValue)
                        .exception(exception)
                        .validate()
        );
        Assertions.assertEquals(INVALID_VALUE, ex.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withInvalidValueAndExceptionAndMessage_shouldThrowExceptionAndMessage() {
        Exception ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> instance.value(invalidValue)
                        .exception(exception)
                        .message(message)
                        .validate()
        );
        Assertions.assertEquals(message, ex.getMessage(), "message should be equal");
    }

}
