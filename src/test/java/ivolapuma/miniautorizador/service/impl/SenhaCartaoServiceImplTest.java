package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class SenhaCartaoServiceImplTest {

    @Autowired
    private SenhaCartaoService service;

    @Autowired
    private MessageSource messages;

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
    }

    @Test
    public void validate_withSenhaInvalid_shouldThorwException() {
        String senha = "x234";
        InvalidSenhaCartaoException e = assertThrows(
                InvalidSenhaCartaoException.class,
                () -> service.validate(senha)
        );
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
    }

}
