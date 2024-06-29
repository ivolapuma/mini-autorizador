package ivolapuma.miniautorizador.validator;

import java.util.Objects;

public class ObjectNotNullValidator extends Validator<Object> {

    @Override
    public boolean isValid() {
        return Objects.nonNull(this.value);
    }

}
