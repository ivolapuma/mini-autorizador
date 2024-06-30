package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SenhaCartaoServiceImplTest {

    private SenhaCartaoService service;

    @BeforeEach
    public void setup() {
        service = new SenhaCartaoServiceImpl();
    }

    @Test
    public void validate_withSenhaValid_shouldRunOk() {
        String senha = "1234";
        assertDoesNotThrow(
                () -> service.validate(senha)
        );
    }

    @Test
    public void validate_withSenhaEmpty_shouldThorwException() {
        String senha = "  ";
        InvalidSenhaCartaoException e = assertThrows(
                InvalidSenhaCartaoException.class,
                () -> service.validate(senha)
        );
        assertEquals("Senha do cartão não pode ser vazia ou nula", e.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withSenhaInvalid_shouldThorwException() {
        String senha = "x234";
        InvalidSenhaCartaoException e = assertThrows(
                InvalidSenhaCartaoException.class,
                () -> service.validate(senha)
        );
        assertEquals("Senha do cartão deve conter 4 dígitos", e.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withSenhaExpectedEqualsToSenhaActual_shouldRunOk() {
        Integer expected = 1234;
        Integer actual = 1234;
        assertDoesNotThrow(
                () -> service.validate(expected, actual)
        );
    }

    @Test
    public void validate_withSenhasDifferent_shouldRunOk() {
        Integer expected = 1234;
        Integer actual = 1111;
        InvalidSenhaCartaoException e = assertThrows(
                InvalidSenhaCartaoException.class,
                () -> service.validate(expected, actual)
        );
        assertEquals("Senha informada inválida", e.getMessage(), "message should be equal");
    }

}
