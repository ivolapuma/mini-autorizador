package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

public class StringNotEmptyValidatorTest extends AbstractValidatorTest<String, IllegalArgumentException> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new StringNotEmptyValidator();
        this.validValue = "some value";
        this.invalidValue = "";
        this.exception = IllegalArgumentException.class;
        this.message = "String cannot be empty or null";
    }

}
