package script;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayStack<T> implements StackADT<T> {

    private Deque<T> data;

    public ArrayStack() {
        this.data = new ArrayDeque<>();
    }

    @Override
    public void push(T value) {
        data.push(value);  
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede hacer pop de una pila vacía");
        }
        return data.pop();  
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede hacer peek de una pila vacía");
        }
        return data.peek();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}