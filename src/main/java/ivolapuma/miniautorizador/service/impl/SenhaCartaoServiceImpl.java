package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import ivolapuma.miniautorizador.validator.BooleanValidator;
import ivolapuma.miniautorizador.validator.RegexValidator;
import ivolapuma.miniautorizador.validator.StringNotEmptyValidator;
import org.springframework.stereotype.Service;

@Service
public class SenhaCartaoServiceImpl implements SenhaCartaoService {

    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_WITH_4_DIGITS_VALIDATOR = new RegexValidator("^\\d{4}$");
    private static final BooleanValidator TRUE_VALIDATOR = new BooleanValidator(true);

    @Override
    public void validate(String senha) throws Throwable {
        STRING_NOT_EMPTY_VALIDATOR.value(senha)
                .exception(IllegalArgumentException.class)
                .message("Senha do cartão não pode ser vazia ou nula")
                .validate();
        STRING_WITH_4_DIGITS_VALIDATOR.value(senha.trim())
                .exception(IllegalArgumentException.class)
                .message("Senha do cartão deve conter 4 dígitos")
                .validate();

    }

    @Override
    public void validate(Integer expected, Integer actual) throws Throwable {
        TRUE_VALIDATOR.value(expected.equals(actual))
                .exception(InvalidSenhaCartaoException.class)
                .message("SENHA_INVALIDA")
                .validate();
    }
}
