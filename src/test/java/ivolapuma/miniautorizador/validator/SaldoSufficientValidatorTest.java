package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

public class SaldoSufficientValidatorTest extends AbstractValidatorTest<BigDecimal> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new SaldoSufficientValidator(BigDecimal.valueOf(500.0));
        this.validValue = BigDecimal.valueOf(499.99);
        this.invalidValue = BigDecimal.valueOf(501.01);;
        this.message = "Saldo insuficiente";
    }

}
