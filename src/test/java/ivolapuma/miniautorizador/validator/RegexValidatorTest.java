package ivolapuma.miniautorizador.validator;

import org.junit.jupiter.api.BeforeEach;

public class RegexValidatorTest extends AbstractValidatorTest<String> {

    @BeforeEach
    @Override
    public void setup() {
        this.instance = new RegexValidator("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        this.validValue = "qualquer@email.com";
        this.invalidValue = "qualqueremail.com";
        this.message = "Email is invalid";
    }

}
