package ivolapuma.miniautorizador.service;

import java.math.BigDecimal;

public interface SaldoService {

    void verifyIfSufficient(BigDecimal saldo, BigDecimal value) throws Throwable;

}
