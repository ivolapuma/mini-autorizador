package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;
import ivolapuma.miniautorizador.exception.UnprocessableEntityException;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import ivolapuma.miniautorizador.service.CartaoService;
import ivolapuma.miniautorizador.service.SaldoService;
import ivolapuma.miniautorizador.validator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoServiceImpl implements CartaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartaoServiceImpl.class);

    private static final BigDecimal SALDO_DEFAULT = BigDecimal.valueOf(500.0);

    private static final ObjectNotNullValidator OBJECT_NOT_NULL_VALIDATOR = new ObjectNotNullValidator();
    private static final StringNotEmptyValidator STRING_NOT_EMPTY_VALIDATOR = new StringNotEmptyValidator();
    private static final RegexValidator STRING_WITH_16_DIGITS_VALIDATOR = new RegexValidator("^\\d{16}$");
    private static final RegexValidator STRING_WITH_4_DIGITS_VALIDATOR = new RegexValidator("^\\d{4}$");
    private static final BooleanValidator FALSE_VALIDATOR = new BooleanValidator(false);
    private static final BooleanValidator TRUE_VALIDATOR = new BooleanValidator(true);;

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private SaldoService saldoService;

//    @Override
//    public boolean existsByNumeroCartao(Long numeroCartao) {
//        return repository.existsById(numeroCartao);
//    }

    @Override
    public CartaoEntity getByNumeroCartao(Long numeroCartao) throws Throwable {
        verifyExists(numeroCartao);
        return repository.findById(numeroCartao).get();
    }

    private void verifyNotExists(Long numeroCartao) throws Throwable {
        FALSE_VALIDATOR.value(repository.existsById(numeroCartao))
                .exception(UnprocessableEntityException.class)
                .message("Cartão já existe na base de dados")
                .validate();
    }

    private void verifyExists(Long numeroCartao) throws Throwable {
        TRUE_VALIDATOR.value(repository.existsById(numeroCartao))
                .exception(NotFoundEntityException.class)
                .message("Cartão não existe na base de dados")
                .validate();
    }

    @Override
    public CartaoEntity create(CartaoEntity cartao) throws Throwable {
        verifyNotExists(cartao.getNumeroCartao());
        cartao.setSaldo(SALDO_DEFAULT);
        return repository.save(cartao);
    }

    @Override
    public BigDecimal getSaldo(Long numeroCartao) throws Throwable {
        verifyExists(numeroCartao);
        CartaoEntity cartao = getByNumeroCartao(numeroCartao);
        return cartao.getSaldo();
    }



    @Override
    public void debitSaldo(Long numeroCartao, BigDecimal value) throws Throwable {
        CartaoEntity cartao = getByNumeroCartao(numeroCartao);
        BigDecimal current = cartao.getSaldo();
        saldoService.verifyIfSufficient(current, value);
        BigDecimal updated = cartao.getSaldo().subtract(value);
        cartao.setSaldo(updated);
        LOGGER.info("Debitando saldo do cartao --> numeroCartao: {} | saldo atual: {} | valor a debitar: {} | novo saldo: {}", cartao.getNumeroCartao(), current, value, updated);
        repository.save(cartao);
    }

    @Override
    public void validate(CreateCartaoRequestDTO request) throws Throwable {
        OBJECT_NOT_NULL_VALIDATOR.value(request)
                .exception(IllegalArgumentException.class)
                .message("Dados da requisição inválidos")
                .validate();
        STRING_NOT_EMPTY_VALIDATOR.value(request.getNumeroCartao())
                .exception(IllegalArgumentException.class)
                .message("Número do cartão não pode ser vazio ou nulo")
                .validate();
        STRING_NOT_EMPTY_VALIDATOR.value(request.getSenha())
                .exception(IllegalArgumentException.class)
                .message("Senha do cartão não pode ser vazia ou nula")
                .validate();
        STRING_WITH_16_DIGITS_VALIDATOR.value(request.getNumeroCartao().trim())
                .exception(IllegalArgumentException.class)
                .message("Número do cartão deve conter 16 dígitos")
                .validate();
        STRING_WITH_4_DIGITS_VALIDATOR.value(request.getSenha().trim())
                .exception(IllegalArgumentException.class)
                .message("Senha do cartão deve conter 4 dígitos")
                .validate();
    }

    @Override
    public void validateNumeroCartao(String numeroCartao) throws Throwable {
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
