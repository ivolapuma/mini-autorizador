package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.exception.BadRequestException;
import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;
import ivolapuma.miniautorizador.exception.UnprocessableEntityException;

import java.math.BigDecimal;

/**
 * Interface que define os serviços referentes ao domínio de Cartões do Miniautorizador.
 */
public interface CartaoService {

//    /**
//     * Serviço que verifica se um Numero de Cartão existe no repositório.
//     * @param numeroCartao
//     * @return
//     */
//    boolean existsByNumeroCartao(Long numeroCartao);

    /**
     * Serviço ...
     * @param numeroCartao
     * @return
     * @throws Throwable
     */
    CartaoEntity getByNumeroCartao(Long numeroCartao) throws NotFoundEntityException;

    /**
     * Serviço que realiza a criação de um Cartão.
     * Para a criaçao do Cartão, o Número do Cartão e Senha devem ser válidos e não podem já existir no repositório.
     * O Cartão será criado com saldo inicial igual a 500.00.
     * @param cartao
     * @return
     */
    CartaoEntity create(CartaoEntity cartao) throws UnprocessableEntityException;

    /**
     * Serviço específco para consultar o saldo de um dado número de cartão.
     * @param numeroCartao
     * @return
     */
    BigDecimal getSaldo(Long numeroCartao) throws NotFoundEntityException;

    /**
     *
     * @param numeroCartao
     * @param value
     */
    void debitSaldo(Long numeroCartao, BigDecimal value) throws NotFoundEntityException, InsufficientSaldoException;

    /**
     * Serviço específico para validar os dados informados na requisição para criação de um cartão.
     * @param request
     */
    void validate(CreateCartaoRequestDTO request) throws BadRequestException;

//    /**
//     * Serviço específico para validar um número de cartão.
//     * @param numeroCartao
//     */
//    void validateNumeroCartao(String numeroCartao);
}
