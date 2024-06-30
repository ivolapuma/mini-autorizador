package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidNumeroCartaoException;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import ivolapuma.miniautorizador.validator.RegexValidator;
import ivolapuma.miniautorizador.validator.StringNotEmptyValidator;
import ivolapuma.miniautorizador.validator.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class NumeroCartaoServiceImpl implements NumeroCartaoService {

    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_WITH_16_DIGITS_VALIDATOR = new RegexValidator("^\\d{16}$");

    @Autowired
    private MessageSource messages;

    @Override
    public void validate(String numeroCartao) throws InvalidNumeroCartaoException {
        try {
            STRING_NOT_EMPTY_VALIDATOR.value(numeroCartao)
                    .message(messages.getMessage("validator.message.numeroCartaoVazio", null, null))
                    .validate();
            STRING_WITH_16_DIGITS_VALIDATOR.value(numeroCartao.trim())
                    .message(messages.getMessage("validator.message.numeroCartaoInvalido", null, null))
                    .validate();
        } catch (ValidatorException e) {
            throw new InvalidNumeroCartaoException(e.getMessage(), e);
        }
    }
}
