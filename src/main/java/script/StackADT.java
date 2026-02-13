package script;

public interface StackADT<T> {
    void push(T value);
    T pop();
    boolean isEmpty();
}
