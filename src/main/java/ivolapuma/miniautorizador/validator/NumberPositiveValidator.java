package ivolapuma.miniautorizador.validator;

/**
 * Classe Validator para verificar se um número é positivo.
 */
public class NumberPositiveValidator extends Validator<Number> {

    private static final double ZERO = 0.0;

    @Override
    public boolean isValid() {
        return this.value.doubleValue() > ZERO;
    }
}
