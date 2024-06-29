package ivolapuma.miniautorizador.validator;

import java.lang.reflect.InvocationTargetException;

public interface ValidatorStrategy {

    default void validate() throws Throwable {
        if (!isValid()) {
            throwException();
        }
    }

    boolean isValid();

    void throwException() throws Throwable;

}
