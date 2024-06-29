package ivolapuma.miniautorizador.validator;

public class NumberPositiveValidator extends Validator<Number> {

    private static final double ZERO = 0.0;

    @Override
    public boolean isValid() {
        return this.value.doubleValue() > ZERO;
    }
}
