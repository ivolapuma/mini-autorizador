package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import ivolapuma.miniautorizador.validator.BooleanValidator;
import ivolapuma.miniautorizador.validator.RegexValidator;
import ivolapuma.miniautorizador.validator.StringNotEmptyValidator;
import ivolapuma.miniautorizador.validator.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class SenhaCartaoServiceImpl implements SenhaCartaoService {

    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_WITH_4_DIGITS_VALIDATOR = new RegexValidator("^\\d{4}$");
    private static final BooleanValidator TRUE_VALIDATOR = new BooleanValidator(true);

    @Autowired
    private MessageSource messages;

    @Override
    public void validate(String senha) throws InvalidSenhaCartaoException {
        try {
            STRING_NOT_EMPTY_VALIDATOR.value(senha)
                    .message(messages.getMessage("validator.message.senhaVazia", null, null))
                    .validate();
            STRING_WITH_4_DIGITS_VALIDATOR.value(senha.trim())
                    .message(messages.getMessage("validator.message.senhaInvalida", null, null))
                    .validate();
        } catch (ValidatorException e) {
            throw new InvalidSenhaCartaoException(e.getMessage(), e);
        }
    }

    @Override
    public void validate(Integer expected, Integer actual) throws InvalidSenhaCartaoException {
        try {
            TRUE_VALIDATOR.value(expected.equals(actual)).validate();
        } catch (ValidatorException e) {
            throw new InvalidSenhaCartaoException(messages.getMessage("validator.message.senhaNaoConfere", null, null), e);
        }
    }
}
