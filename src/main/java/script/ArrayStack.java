package script;

import java.util.EmptyStackException;

public class ArrayStack<T> {

    private Object[] data;
    private int top; // cantidad de elementos (también índice del siguiente libre)

    public ArrayStack() {
        this(16);
    }

    public ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) initialCapacity = 16;
        data = new Object[initialCapacity];
        top = 0;
    }

    public void push(T value) {
        if (top == data.length) grow();
        data[top++] = value;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T value = (T) data[--top];
        data[top] = null; // evitar memory leak
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return (T) data[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    private void grow() {
        Object[] bigger = new Object[data.length * 2];
        System.arraycopy(data, 0, bigger, 0, data.length);
        data = bigger;
    }
}
