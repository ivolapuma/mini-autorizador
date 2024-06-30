package ivolapuma.miniautorizador.validator;

import java.util.Objects;

/**
 * Classe Validator para verificar se um objeto não é nulo.
 */
public class ObjectNotNullValidator extends Validator<Object> {

    @Override
    public boolean isValid() {
        return Objects.nonNull(this.value);
    }

}
