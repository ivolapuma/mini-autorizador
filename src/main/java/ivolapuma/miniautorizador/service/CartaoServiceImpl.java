package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.controller.CartaoController;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoServiceImpl implements CartaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartaoServiceImpl.class);

    private static final BigDecimal SALDO_DEFAULT = BigDecimal.valueOf(500.0);

    @Autowired
    private CartaoRepository repository;

    @Override
    public CartaoEntity criarCartao(CartaoEntity cartao) {
        // TODO: validar request
        // TODO: verificar se cartao já existe no repositorio
        // TODO: gravar cartao no repositorio
        cartao.setSaldo(SALDO_DEFAULT);
        CartaoEntity saved = repository.save(cartao);
        return saved;
    }

    @Override
    public BigDecimal consultarSaldo(Long numeroCartao) {
        CartaoEntity cartao = repository.findById(numeroCartao).get();
        return cartao.getSaldo();
    }

    @Override
    public CartaoEntity buscarCartao(Long numeroCartao) {
        return repository.findById(numeroCartao).get();
    }

    @Override
    public void atualizarSaldo(CartaoEntity cartao, BigDecimal valor) {
        LOGGER.info("Atualizando saldo do cartao --> numeroCartao: {} | saldo: {} | valor a debitar: {}", cartao.getNumeroCartao(), cartao.getSaldo(), valor);
        BigDecimal novoSaldo = cartao.getSaldo().subtract(valor);
        cartao.setSaldo(novoSaldo);
        repository.save(cartao);
    }
}
