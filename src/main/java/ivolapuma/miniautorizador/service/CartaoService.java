package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
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
    CartaoEntity create(CartaoEntity cartao) throws Throwable;

    /**
     * Serviço específco para consultar o saldo de um dado número de cartão.
     * @param numeroCartao
     * @return
     */
    BigDecimal getSaldo(Long numeroCartao) throws Throwable;

    CartaoEntity getByNumeroCartao(Long numeroCartao);

    void updateSaldo(CartaoEntity cartao, BigDecimal value);

    /**
     * Serviço específico para validar os dados informados na requisição para criação de um cartão.
     * @param request
     */
    void validate(CreateCartaoRequestDTO request) throws Throwable;

    /**
     * Serviço específico para validar um número de cartão.
     * @param numeroCartao
     */
    void validateNumeroCartao(String numeroCartao) throws Throwable;
}
