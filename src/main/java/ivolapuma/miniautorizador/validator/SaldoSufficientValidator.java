package ivolapuma.miniautorizador.validator;

import java.math.BigDecimal;

/**
 * Classe Validator para verificar se o Saldo informado é suficiente para o valor a ser realizado.
 */
public class SaldoSufficientValidator extends Validator<BigDecimal> {

    private BigDecimal saldo;

    public SaldoSufficientValidator(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean isValid() {
        return this.value.compareTo(this.saldo) <= 0;
    }
}
