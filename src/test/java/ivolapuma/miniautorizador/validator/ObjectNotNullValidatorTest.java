package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

public class ObjectNotNullValidatorTest extends AbstractValidatorTest<Object, IllegalArgumentException> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new ObjectNotNullValidator();
        this.validValue = new Object();
        this.invalidValue = null;
        this.exception = IllegalArgumentException.class;
        this.message = "Object should not be null";
    }

}
