package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.service.NumeroCartaoService;
import ivolapuma.miniautorizador.validator.RegexValidator;
import ivolapuma.miniautorizador.validator.StringNotEmptyValidator;
import org.springframework.stereotype.Service;

@Service
public class NumeroCartaoServiceImpl implements NumeroCartaoService {

    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_WITH_16_DIGITS_VALIDATOR = new RegexValidator("^\\d{16}$");

    @Override
    public void validate(String numeroCartao) throws Throwable {
        STRING_NOT_EMPTY_VALIDATOR.value(numeroCartao)
                .exception(IllegalArgumentException.class)
                .message("Número do cartão não pode ser vazio ou nulo")
                .validate();
        STRING_WITH_16_DIGITS_VALIDATOR.value(numeroCartao.trim())
                .exception(IllegalArgumentException.class)
                .message("Número do cartão deve conter 16 dígitos")
                .validate();
    }
}
