package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

public class NumberPositiveValidatorTest extends AbstractValidatorTest<Number> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new NumberPositiveValidator();
        this.validValue = 0.1;
        this.invalidValue = 0;
        this.message = "Number value is invalid";
    }
}
