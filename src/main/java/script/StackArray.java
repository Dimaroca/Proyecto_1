package script;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackArray<T> implements ADTStack<T> {

    private Deque<T> items;

    public StackArray() {
        this.items = new ArrayDeque<>();
    }

    @Override
    public void push(T item) {
        items.push(item);
    }

    @Override
    public T pop() {
        if (items.isEmpty()) {
            throw new EmptyStackException("No se puede hacer pop de una pila vacía");
        }
        return items.pop();
    }

    @Override
    public T peek() {
        if (items.isEmpty()) {
            throw new EmptyStackException("No se puede hacer peek de una pila vacía");
        }
        return items.peek();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
/**
 * Excepción lanzada cuando se intenta acceder
 * a elementos de una pila vacía.
 */
class EmptyStackException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message mensaje de error
     */
    public EmptyStackException(String message) {
        super(message);
    }
}