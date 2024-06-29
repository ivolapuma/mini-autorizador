package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.SaldoInsufficientException;
import ivolapuma.miniautorizador.service.SaldoService;
import ivolapuma.miniautorizador.validator.SaldoSufficientValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaldoServiceImpl implements SaldoService {

    @Override
    public void verifyIfSufficient(BigDecimal saldo, BigDecimal value) throws Throwable {
        new SaldoSufficientValidator(saldo).value(value)
                .exception(SaldoInsufficientException.class)
                .message("SALDO_INSUFICIENTE")
                .validate();
    }
}
