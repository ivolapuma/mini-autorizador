package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidNumeroCartaoException;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumeroCartaoServiceImplTest {

    private NumeroCartaoService service;

    @BeforeEach
    public void setup() {
        service = new NumeroCartaoServiceImpl();
    }

    @Test
    public void validate_withNumeroCartaoValid_shouldRunOk() {
        String numeroCartao = "1111222233334444";
        assertDoesNotThrow(
                () -> service.validate(numeroCartao)
        );
    }

    @Test
    public void validate_withNumeroCartaoEmpty_shouldThorwException() {
        String numeroCartao = "  ";
        InvalidNumeroCartaoException e = assertThrows(
                InvalidNumeroCartaoException.class,
                () -> service.validate(numeroCartao)
        );
        assertEquals("Número do cartão não pode ser vazio ou nulo", e.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withNumeroCartaoInvalid_shouldThorwException() {
        String numeroCartao = "x111222233334444";
        InvalidNumeroCartaoException e = assertThrows(
                InvalidNumeroCartaoException.class,
                () -> service.validate(numeroCartao)
        );
        assertEquals("Número do cartão deve conter 16 dígitos", e.getMessage(), "message should be equal");
    }

}
