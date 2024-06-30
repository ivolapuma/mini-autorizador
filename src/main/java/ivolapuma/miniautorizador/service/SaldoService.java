package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.exception.InsufficientSaldoException;

import java.math.BigDecimal;

/**
 * Interface que define os serviços referentes ao domínio de Saldo do Cartão do Miniautorizador.
 */
public interface SaldoService {

    /**
     * Serviço que retorna o valor default para o Saldo inicial de qualquer Cartão da aplicação.
     * Caso por algum motivo o valor default não esteja corretamente definido, é retornado o valor zero (0.00).
     *
     * @return
     */
    BigDecimal getSaldoDefault();

    /**
     * Serviço específico para verificar se o Saldo é suficiente para o valor da transação a ser realizada.
     *
     * @param saldo Valor do Saldo do Cartão.
     * @param value Valor da Transação.
     * @throws InsufficientSaldoException Lançado quando o valor do Saldo é insuficiente para o valor da Transação a ser realizada.
     */
    void verifyIfSufficient(BigDecimal saldo, BigDecimal value) throws InsufficientSaldoException;
}
