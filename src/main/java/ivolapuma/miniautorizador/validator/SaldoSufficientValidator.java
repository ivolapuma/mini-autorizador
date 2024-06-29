package ivolapuma.miniautorizador.validator;

import java.math.BigDecimal;

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
