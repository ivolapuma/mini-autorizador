package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidNumeroCartaoException;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NumeroCartaoServiceImplTest {

    @Autowired
    private NumeroCartaoService service;

    @Autowired
    private MessageSource messages;

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
    }

    @Test
    public void validate_withNumeroCartaoInvalid_shouldThorwException() {
        String numeroCartao = "x111222233334444";
        InvalidNumeroCartaoException e = assertThrows(
                InvalidNumeroCartaoException.class,
                () -> service.validate(numeroCartao)
        );
    }

}
