package ivolapuma.miniautorizador.validator;

import java.util.Objects;

/**
 * Classe da aplicação que valida se uma String não é vazia ou nula.
 */
public class StringNotEmptyValidator extends Validator<String> {

    @Override
    public boolean isValid() {
        return Objects.nonNull(this.value) && !this.value.isEmpty();
    }

}
