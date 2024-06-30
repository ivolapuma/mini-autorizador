package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

public class NumberEqualsToValidatorTest extends AbstractValidatorTest<Number> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new NumberEqualsToValidator(10);
        this.validValue = 10;
        this.invalidValue = 11;
        this.message = "Number value is invalid";
    }

}
