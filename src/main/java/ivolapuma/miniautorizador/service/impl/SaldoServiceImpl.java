package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import ivolapuma.miniautorizador.service.SaldoService;
import ivolapuma.miniautorizador.validator.SaldoSufficientValidator;
import ivolapuma.miniautorizador.validator.exception.ValidatorException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaldoServiceImpl implements SaldoService {

    @Override
    public void verifyIfSufficient(BigDecimal saldo, BigDecimal value) throws InsufficientSaldoException {
        try {
            new SaldoSufficientValidator(saldo).value(value)
                    .message("SALDO_INSUFICIENTE")
                    .validate();
        } catch (ValidatorException e) {
            throw new InsufficientSaldoException(e.getMessage(), e);
        }
    }
}
