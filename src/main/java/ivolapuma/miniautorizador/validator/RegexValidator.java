package ivolapuma.miniautorizador.validator;

import java.util.regex.Pattern;

/**
 * Classe Validator para verificar se uma String confere com uma regra de Expressão Regular (RegEx).
 */
public class RegexValidator extends Validator<String> {

    private final String regex;

    public RegexValidator(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean isValid() {
        return Pattern.compile(this.regex).matcher(this.value).matches();
    }
}
