package script;
import java.util.ArrayDeque;
import java.util.Deque;

public class StackValidator {
    Deque<Boolean> stack = new ArrayDeque<>();

    public void push(boolean info){
        stack.push(info);
    }

    public void pop(){
        stack.pop();
    }

    public boolean Usage(){
        Boolean[] information = stack.toArray(new Boolean[0]);

        for (int i=0; i < information.length; i++){
            if(information[i]==null){
                return false;
            }

            if (information[i]==false){
                return false;
            }
        }

        return true;
    }
}
