package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

public class BooleanValidatorTest extends AbstractValidatorTest<Boolean> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new BooleanValidator(true);
        this.validValue = true;
        this.invalidValue = false;
        this.message = "Boolean value is invalid";
    }

}
