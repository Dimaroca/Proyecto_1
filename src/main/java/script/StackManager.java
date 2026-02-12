package script; 
import java.util.ArrayDeque;
import java.util.Deque;

public class StackManager {
    
    private Deque<byte[]> stack = new ArrayDeque();

    public void push(byte[] info){
        stack.push(info);
    }

    public byte[] pop(){
        return stack.pop();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }
}
