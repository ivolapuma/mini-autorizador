package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import ivolapuma.miniautorizador.validator.BooleanValidator;
import ivolapuma.miniautorizador.validator.RegexValidator;
import ivolapuma.miniautorizador.validator.StringNotEmptyValidator;
import ivolapuma.miniautorizador.validator.exception.ValidatorException;
import org.springframework.stereotype.Service;

@Service
public class SenhaCartaoServiceImpl implements SenhaCartaoService {

    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_WITH_4_DIGITS_VALIDATOR = new RegexValidator("^\\d{4}$");
    private static final BooleanValidator TRUE_VALIDATOR = new BooleanValidator(true);

    @Override
    public void validate(String senha) throws InvalidSenhaCartaoException {
        try {
            STRING_NOT_EMPTY_VALIDATOR.value(senha)
                    .message("Senha do cartão não pode ser vazia ou nula")
                    .validate();
            STRING_WITH_4_DIGITS_VALIDATOR.value(senha.trim())
                    .message("Senha do cartão deve conter 4 dígitos")
                    .validate();
        } catch (ValidatorException e) {
            throw new InvalidSenhaCartaoException(e.getMessage(), e);
        }

    }

    @Override
    public void validate(Integer expected, Integer actual) throws InvalidSenhaCartaoException {
        try {
            TRUE_VALIDATOR.value(expected.equals(actual))
                    .message("SENHA_INVALIDA")
                    .validate();
        } catch (ValidatorException e) {
            throw new InvalidSenhaCartaoException(e.getMessage(), e);
        }
    }
}
