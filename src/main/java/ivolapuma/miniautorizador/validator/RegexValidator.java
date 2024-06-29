package ivolapuma.miniautorizador.validator;

import java.util.regex.Pattern;

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
