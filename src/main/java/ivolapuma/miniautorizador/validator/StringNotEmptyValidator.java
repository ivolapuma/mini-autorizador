package ivolapuma.miniautorizador.validator;

import java.util.Objects;

/**
 * Classe Validator para verificar se uma String não é vazia ou nula.
 */
public class StringNotEmptyValidator extends Validator<String> {

    @Override
    public boolean isValid() {
        return Objects.nonNull(this.value) && !this.value.trim().isEmpty();
    }

}
