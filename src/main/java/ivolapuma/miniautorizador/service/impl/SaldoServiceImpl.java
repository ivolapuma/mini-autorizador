package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import ivolapuma.miniautorizador.service.SaldoService;
import ivolapuma.miniautorizador.validator.*;
import ivolapuma.miniautorizador.validator.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaldoServiceImpl implements SaldoService {

    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_NUMBER_POSITIVE_VALIDATOR  = new RegexValidator("^\\d+(\\.\\d+)?$");

    @Value("${miniautorizador.cartao.saldo-default}")
    private String saldoDefault;

    @Autowired
    private MessageSource messages;

    @Override
    public BigDecimal getSaldoDefault() {
        try {
            STRING_NOT_EMPTY_VALIDATOR.value(saldoDefault).validate();
            STRING_NUMBER_POSITIVE_VALIDATOR.value(saldoDefault).validate();
            return BigDecimal.valueOf(Double.valueOf(saldoDefault));
        } catch (ValidatorException e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public void verifyIfSufficient(BigDecimal saldo, BigDecimal value) throws InsufficientSaldoException {
        try {
            new SaldoSufficientValidator(saldo).value(value).validate();
        } catch (ValidatorException e) {
            throw new InsufficientSaldoException(messages.getMessage("validator.message.saldoInsuficiente", null, null), e);
        }
    }
}
