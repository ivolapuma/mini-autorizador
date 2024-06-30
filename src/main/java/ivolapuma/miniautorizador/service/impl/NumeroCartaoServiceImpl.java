package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.exception.InvalidNumeroCartaoException;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import ivolapuma.miniautorizador.validator.RegexValidator;
import ivolapuma.miniautorizador.validator.StringNotEmptyValidator;
import ivolapuma.miniautorizador.validator.exception.ValidatorException;
import org.springframework.stereotype.Service;

@Service
public class NumeroCartaoServiceImpl implements NumeroCartaoService {

    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_WITH_16_DIGITS_VALIDATOR = new RegexValidator("^\\d{16}$");

    @Override
    public void validate(String numeroCartao) throws InvalidNumeroCartaoException {
        try {
            STRING_NOT_EMPTY_VALIDATOR.value(numeroCartao)
                    .message("Número do cartão não pode ser vazio ou nulo")
                    .validate();
            STRING_WITH_16_DIGITS_VALIDATOR.value(numeroCartao.trim())
                    .message("Número do cartão deve conter 16 dígitos")
                    .validate();
        } catch (ValidatorException e) {
            throw new InvalidNumeroCartaoException(e.getMessage(), e);
        }
    }
}
