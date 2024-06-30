package ivolapuma.miniautorizador.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Classe Builder genérica para criar instâncias de qualquer Classe.
 *
 * Exemplo de uso:
 *
 * CartaoEntity cartao =
 *     GenericBuilder.of(CartaoEnity::new)
 *         .with(CartaoEnity::setNumeroCartao, 1111222233334444L)
 *         .with(CartaoEnity::setSenha, 1234)
 *         .build();
 *
 * @param <T> Tipo da classe a ser instanciada.
 */
public class GenericBuilder<T> {

    private final Supplier<T> instantiator;

    private List<Consumer<T>> instanceModifiers = new ArrayList<>();

    public GenericBuilder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> GenericBuilder<T> of(Supplier<T> instantiator) {
        return new GenericBuilder<T>(instantiator);
    }

    public <U> GenericBuilder<T> with(BiConsumer<T, U> consumer, U value) {
        Consumer<T> c = instance -> consumer.accept(instance, value);
        instanceModifiers.add(c);
        return this;
    }

    public T build() {
        T value = instantiator.get();
        instanceModifiers.forEach(modifier -> modifier.accept(value));
        instanceModifiers.clear();
        return value;
    }

}