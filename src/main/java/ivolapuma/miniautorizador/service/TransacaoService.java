package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.exception.BadRequestException;
import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;

/**
 * Interface que define os serviços referentes ao domínio de Transação do Miniautorizador.
 */
public interface TransacaoService {

    /**
     * Serviço específico para validar os dados informados na requisição de realização de uma Transação.
     *
     * @param request DTO com os dados da requisição de realização de Transação.
     * @throws BadRequestException Lançado quando algum dado está inválido.
     */
    void validate(ExecuteTransacaoRequestDTO request) throws BadRequestException;

    /**
     * Serviço que realiza uma Transação de débito do Saldo do Cartão.
     *
     * @param transacao Entidade da transação a ser realizada.
     * @return Entidade da transação realizada.
     * @throws InvalidSenhaCartaoException Lançado quando a senha informada na Transação não confere com a senha do Cartão.
     * @throws InsufficientSaldoException Lançado quando o saldo do Cartão é insuficiente para o valor informado para a Transação.
     * @throws NotFoundEntityException Lançado quando o Número do Cartão não é localizado no repositório.
     */
    TransacaoEntity execute(TransacaoEntity transacao) throws InvalidSenhaCartaoException, InsufficientSaldoException, NotFoundEntityException;

    /**
     * Serviço específico para persistir os dados da Transação (ou tentativa de Transação) no log do Sistema.
     *
     * @param transacao Entidade da transação a ser persistida.
     */
    void log(TransacaoEntity transacao);
}
