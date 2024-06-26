package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.entity.CartaoEntity;

import java.math.BigDecimal;

/**
 * Interface que define os serviços referentes ao domínio de Cartões do Miniautorizador.
 */
public interface CartaoService {

    /**
     * Serviço que realiza a criação de um Cartão.
     * Para a criaçao do Cartão, o Número do Cartão e Senha devem ser válidos e não podem já existir no repositório.
     * O Cartão será criado com saldo inicial igual a 500.00.
     * @param cartao
     * @return
     */
    CartaoEntity criarCartao(CartaoEntity cartao);

    BigDecimal consultarSaldo(Long numeroCartao);
}
