package ivolapuma.miniautorizador.validator;

import ivolapuma.miniautorizador.validator.exception.ValidatorException;
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
 */
abstract public class AbstractValidatorTest<T> {

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
     * Deve conter a mensagem a ser lançada com a exceção nos casos em que uma Exception é
     * definida para ser lançada em caso de valor inválido.
     */
    protected String message;

    /**
     * Sugerimos definir este método na classe concreta para definir os valores dos atributos:
     * instance
     * validValue
     * invalidValue
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
    public void validate_withValidValue_shouldNotThrowException() {
        Assertions.assertDoesNotThrow(
                ()->instance.value(validValue).validate()
        );
    }

    @Test
    public void validate_withInvalidValue_shouldThrowException() {
        ValidatorException ex = Assertions.assertThrows(
                ValidatorException.class,
                () -> instance.value(invalidValue).validate()
        );
        Assertions.assertEquals(INVALID_VALUE, ex.getMessage(), "message should be equal");
    }

}
