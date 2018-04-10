package Lab5.GenClassificator.Data;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (final Exception ex) {
            // Implement your own exception handling logic here..
            // For example:
            System.out.println("ThrowingConsumer: handling an exception...");
            // Or ...
            ex.printStackTrace();
            
            throw new RuntimeException(ex);
        }
    }

    void acceptThrows(T elem) throws Exception;
}