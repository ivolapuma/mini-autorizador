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

    /**
     * Serviço específico para validar os dados informados na requisição de criação de um Cartão.
     *
     * @param request DTO com os dados da requisição de criação do Cartão.
     * @throws BadRequestException Lançado quando algum dado está inválido.
     */
    void validate(CreateCartaoRequestDTO request) throws BadRequestException;

    /**
     * Serviço que realiza a criação de um Cartão.
     * O Cartão será criado com saldo inicial definido pelo serviço SaldoService.getSaldoDefault().
     *
     * @param cartao Entidade do cartão a ser criado.
     * @return Entidade do cartão criado.
     * @throws UnprocessableEntityException Lançado quando o Cartão já existe no repositório.
     */
    CartaoEntity create(CartaoEntity cartao) throws UnprocessableEntityException;

    /**
     * Serviço de busca de Cartão pelo Número de Cartão.
     *
     * @param numeroCartao Número do Cartão a ser localizado.
     * @return
     * @throws NotFoundEntityException Lançado quando o Cartão não é localizado no repositório.
     */
    CartaoEntity getByNumeroCartao(Long numeroCartao) throws NotFoundEntityException;

    /**
     * Serviço específco para consultar o saldo de um dado Número de Cartão.
     *
     * @param numeroCartao Número do Cartão a ter o saldo consultado.
     * @return
     * @throws NotFoundEntityException Lançado quando o Cartão não é localizado no repositório.
     */
    BigDecimal getSaldo(Long numeroCartao) throws NotFoundEntityException;

    /**
     * Serviço que realiza o débito de algum valor no saldo do Cartão.
     *
     * @param numeroCartao Número do Cartão a ser debitado.
     * @param value Valor a ser debitado do saldo do Cartão.
     * @return
     * @throws NotFoundEntityException Lançado quando o Número de Cartão não é localizado no repositório.
     * @throws InsufficientSaldoException Lançado quando o saldo é insuficiente para o valor informado.
     */
    CartaoEntity debitSaldo(Long numeroCartao, BigDecimal value) throws NotFoundEntityException, InsufficientSaldoException;

}
