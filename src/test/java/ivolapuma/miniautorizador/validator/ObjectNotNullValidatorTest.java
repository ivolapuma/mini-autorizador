package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

public class ObjectNotNullValidatorTest extends AbstractValidatorTest<Object> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new ObjectNotNullValidator();
        this.validValue = new Object();
        this.invalidValue = null;
        this.message = "Object should not be null";
    }

}
