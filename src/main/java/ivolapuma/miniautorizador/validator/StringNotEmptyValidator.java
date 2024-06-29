package ivolapuma.miniautorizador.validator;

import java.util.Objects;

public class StringNotEmptyValidator extends Validator<String> {

    @Override
    public boolean isValid() {
        return Objects.nonNull(this.value) && !this.value.isEmpty();
    }

}
