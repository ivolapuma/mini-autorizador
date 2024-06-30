package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.exception.InsufficientSaldoException;

import java.math.BigDecimal;

public interface SaldoService {

    /**
     *
     * @return
     */
    BigDecimal getSaldoDefault();

    void verifyIfSufficient(BigDecimal saldo, BigDecimal value) throws InsufficientSaldoException;
}
