package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.exception.*;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import ivolapuma.miniautorizador.service.CartaoService;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import ivolapuma.miniautorizador.service.SaldoService;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import ivolapuma.miniautorizador.validator.*;
import ivolapuma.miniautorizador.validator.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartaoServiceImpl implements CartaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartaoServiceImpl.class);

    private static final BigDecimal SALDO_DEFAULT = BigDecimal.valueOf(500.0);

    private static final ObjectNotNullValidator OBJECT_NOT_NULL_VALIDATOR = new ObjectNotNullValidator();
    private static final BooleanValidator FALSE_VALIDATOR = new BooleanValidator(false);
    private static final BooleanValidator TRUE_VALIDATOR = new BooleanValidator(true);;

    @Autowired
    private MessageSource messages;

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private SaldoService saldoService;

    @Autowired
    private NumeroCartaoService numeroCartaoService;

    @Autowired
    private SenhaCartaoService senhaCartaoService;

    @Override
    public CartaoEntity create(CartaoEntity cartao) throws UnprocessableEntityException {
        verifyNotExistsCartao(cartao.getNumeroCartao());
        cartao.setSaldo(SALDO_DEFAULT);
        return repository.save(cartao);
    }

    private void verifyNotExistsCartao(Long numeroCartao) throws UnprocessableEntityException {
        try {
            FALSE_VALIDATOR.value(repository.existsById(numeroCartao))
                    .message(messages.getMessage("validator.message.cartaoJaExistente", null, null))
                    .validate();
        } catch (ValidatorException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
    }

    @Override
    public CartaoEntity getByNumeroCartao(Long numeroCartao) throws NotFoundEntityException {
        Optional<CartaoEntity> optional = repository.findById(numeroCartao);
        verifyIsPresent(optional);
        return optional.get();
    }

    private void verifyIsPresent(Optional<CartaoEntity> optional) throws NotFoundEntityException {
        try {
            TRUE_VALIDATOR.value(optional.isPresent())
                    .message(messages.getMessage("validator.message.cartaoInexistente", null, null))
                    .validate();
        } catch (ValidatorException e) {
            throw new NotFoundEntityException(e.getMessage(), e);
        }
    }

    @Override
    public BigDecimal getSaldo(Long numeroCartao) throws NotFoundEntityException {
        return getByNumeroCartao(numeroCartao).getSaldo();
    }

    @Override
    public void debitSaldo(Long numeroCartao, BigDecimal value) throws NotFoundEntityException, InsufficientSaldoException {
        CartaoEntity cartao = getByNumeroCartao(numeroCartao);
        BigDecimal current = cartao.getSaldo();
        saldoService.verifyIfSufficient(current, value);
        BigDecimal updated = cartao.getSaldo().subtract(value);
        cartao.setSaldo(updated);
        LOGGER.info("Debitando saldo do cartao --> numeroCartao: {} | saldo atual: {} | valor a debitar: {} | novo saldo: {}", cartao.getNumeroCartao(), current, value, updated);
        repository.save(cartao);
    }

    @Override
    public void validate(CreateCartaoRequestDTO request) throws BadRequestException {
        try {
            OBJECT_NOT_NULL_VALIDATOR.value(request)
                    .message(messages.getMessage("validator.message.requisicaoInvalida", null, null))
                    .validate();
            numeroCartaoService.validate(request.getNumeroCartao());
            senhaCartaoService.validate(request.getSenha());
        } catch (ValidatorException | InvalidNumeroCartaoException | InvalidSenhaCartaoException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

}
