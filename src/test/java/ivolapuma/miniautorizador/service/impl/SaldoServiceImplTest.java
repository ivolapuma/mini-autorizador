package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaldoServiceImplTest {

    @InjectMocks
    private SaldoServiceImpl service;

    @Mock
    private MessageSource messages;

    @Test
    public void getSaldoDefault_shouldReturnSaldoDefault() throws NoSuchFieldException, IllegalAccessException {
        setField("saldoDefault", "500.0");
        BigDecimal saldoDefault = service.getSaldoDefault();
        assertTrue(BigDecimal.valueOf(500.0).compareTo(saldoDefault) == 0);
    }

    @Test
    public void getSaldoDefault_withNoDefaultSet_shouldReturnZero() throws NoSuchFieldException, IllegalAccessException {
        setField("saldoDefault", "");
        BigDecimal saldoDefault = service.getSaldoDefault();
        assertTrue(BigDecimal.valueOf(0.0).compareTo(saldoDefault) == 0);
    }

    @Test
    public void getSaldoDefault_withNegative_shouldReturnZero() throws NoSuchFieldException, IllegalAccessException {
        setField("saldoDefault", "-500.0");
        BigDecimal saldoDefault = service.getSaldoDefault();
        assertTrue(BigDecimal.valueOf(0.0).compareTo(saldoDefault) == 0);
    }

    @Test
    public void getSaldoDefault_withNoNumber_shouldReturnZero() throws NoSuchFieldException, IllegalAccessException {
        setField("saldoDefault", "abc");
        BigDecimal saldoDefault = service.getSaldoDefault();
        assertTrue(BigDecimal.valueOf(0.0).compareTo(saldoDefault) == 0);
    }

    @Test
    public void verifyIfSufficient_withSaldoSufficient_shouldRunOk() {
        BigDecimal saldo = BigDecimal.valueOf(500.0);
        BigDecimal valor = BigDecimal.valueOf(10.0);
        assertDoesNotThrow(
                () -> service.verifyIfSufficient(saldo, valor)
        );
    }

    @Test
    public void verifyIfSufficient_withOneCentMore_shouldRunOk() {
        BigDecimal saldo = BigDecimal.valueOf(500.0);
        BigDecimal valor = BigDecimal.valueOf(499.99);
        assertDoesNotThrow(
                () -> service.verifyIfSufficient(saldo, valor)
        );
    }

    @Test
    public void verifyIfSufficient_withSaldoEqualsToValor_shouldRunOk() {
        BigDecimal saldo = BigDecimal.valueOf(500.0);
        BigDecimal valor = BigDecimal.valueOf(500.0);
        assertDoesNotThrow(
                () -> service.verifyIfSufficient(saldo, valor)
        );
    }

    @Test
    public void verifyIfSufficient_withSaldoInsufficient_shouldThrowException() throws InsufficientSaldoException {
        BigDecimal saldo = BigDecimal.valueOf(500.0);
        BigDecimal valor = BigDecimal.valueOf(1000.0);
        when(messages.getMessage(anyString(), any(), any())).thenReturn("Mensagem de erro");
        assertThrows(
                InsufficientSaldoException.class,
                () -> service.verifyIfSufficient(saldo, valor)
        );
    }

    @Test
    public void verifyIfSufficient_withSaldoOneCentMinus_shouldThrowException() throws InsufficientSaldoException {
        BigDecimal saldo = BigDecimal.valueOf(500.0);
        BigDecimal valor = BigDecimal.valueOf(500.01);
        when(messages.getMessage(anyString(), any(), any())).thenReturn("Mensagem de erro");
        assertThrows(
                InsufficientSaldoException.class,
                () -> service.verifyIfSufficient(saldo, valor)
        );
    }

    private void setField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = SaldoServiceImpl.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(service, value);
    }

}
