package ivolapuma.miniautorizador.validator;

/**
 * Classe Validator para valores booleanos.
 */
public class BooleanValidator extends Validator<Boolean> {

    private boolean other;

    public BooleanValidator(boolean other) {
        this.other = other;
    }

    @Override
    public boolean isValid() {
        return this.value == this.other;
    }
}
