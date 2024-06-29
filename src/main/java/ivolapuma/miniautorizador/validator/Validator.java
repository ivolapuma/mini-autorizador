package ivolapuma.miniautorizador.validator;

abstract public class Validator<T> implements ValidatorStrategy {

    private static final String MESSAGE_DEFAULT = "Invalid value";

    protected T value;
    private Class<?> exception;
    private String message;

    public Validator() {
        this.value = value;
        this.exception = Exception.class;
        this.message = MESSAGE_DEFAULT;
    }

    public Validator<T> value(T obj) {
        this.value = obj;
        return this;
    }

    public <E extends Exception> Validator<T> exception(Class<E> type, String message) {
        this.exception = type;
        this.message = message;
        return this;
    }

    public <E extends Exception> Validator<T> exception(Class<E> type) {
        this.exception = type;
        return this;
    }

    public Validator<T> message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public void throwException() throws Throwable {
        throw (Throwable) exception.getDeclaredConstructor(String.class).newInstance(message);
    }
}
