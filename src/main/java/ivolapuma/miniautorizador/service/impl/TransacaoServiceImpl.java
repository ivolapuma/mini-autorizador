package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.service.*;
import ivolapuma.miniautorizador.validator.NumberPositiveValidator;
import ivolapuma.miniautorizador.validator.ObjectNotNullValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransacaoServiceImpl.class);

    private static final ObjectNotNullValidator OBJECT_NOT_NULL_VALIDATOR = new ObjectNotNullValidator();
    private static final NumberPositiveValidator NUMBER_POSITIVE_VALIDATOR = new NumberPositiveValidator();

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private NumeroCartaoService numeroCartaoService;

    @Autowired
    private SenhaCartaoService senhaCartaoService;

    @Autowired
    private SaldoService saldoService;

    @Override
    public void validate(ExecuteTransacaoRequestDTO request) throws Throwable {
        OBJECT_NOT_NULL_VALIDATOR.value(request)
                .exception(IllegalArgumentException.class)
                .message("Dados da requisição inválidos")
                .validate();
        NUMBER_POSITIVE_VALIDATOR.value(request.getValor())
                .exception(IllegalArgumentException.class)
                .message("Valor da transação deve ser um número positivo")
                .validate();
        numeroCartaoService.validate(request.getNumeroCartao());
        senhaCartaoService.validate(request.getSenhaCartao());
    }

    @Override
    public TransacaoEntity execute(TransacaoEntity transacao) throws Throwable {
        Long numeroCartao = transacao.getNumeroCartao();
        CartaoEntity cartao = cartaoService.getByNumeroCartao(numeroCartao);
        senhaCartaoService.validate(cartao.getSenha(), transacao.getSenhaCartao());
        transacao.setSaldo(cartao.getSaldo());
        saldoService.verifyIfSufficient(transacao.getSaldo(), transacao.getValor());
        cartaoService.debitSaldo(numeroCartao, transacao.getValor());
        transacao.setSucesso(true);
        logTransacao(transacao);
        return transacao;
    }

    private static void logTransacao(TransacaoEntity transacao) {
        transacao.setTimestamp(LocalDateTime.now());
        String message = transacao.isSucesso() ? "SUCESSO" : "FALHA";
        LOGGER.warn("Transação concluída com {}! --> Transacao: {}", message, transacao);
    }

}
