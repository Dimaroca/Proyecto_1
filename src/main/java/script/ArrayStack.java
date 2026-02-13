package script;

public class ArrayStack<T> implements StackADT<T>{

    private Object[] data;
    private int top; 

    public ArrayStack() {
        this(16);
    }

    public ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) initialCapacity = 16;
        data = new Object[initialCapacity];
        top = 0;
    }

    @Override
    public void push(T value) {
        if (top == data.length) grow();
        data[top++] = value;
    }

    @Override
    public T peek(){
        return (T) data[top--];
    }

    @Override
    public T pop(){
        T info = (T) data[top--];
        data[top]=null;
        return info;
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
