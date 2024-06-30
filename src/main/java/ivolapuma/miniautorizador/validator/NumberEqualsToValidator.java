package ivolapuma.miniautorizador.validator;

/**
 * Classe Validator para verificar equivalência de valores numéricos.
 */
public class NumberEqualsToValidator extends Validator<Number> {

    private final Number other;

    public NumberEqualsToValidator(Number other) {
        this.other = other;
    }

    @Override
    public boolean isValid() {
        return this.value.equals(this.other);
    }
}
