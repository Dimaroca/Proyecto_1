package script;
import java.util.List;

public class Interpreter {
    public boolean execute(List<Opcode> script) {

        StackManager stack = new StackManager();
        StackValidator validator = new StackValidator();

        for (Opcode op : script) {
            op.execute(stack, validator);
        }

        if (stack.isEmpty()){
            return false;
        } 

        byte[] result = stack.pop();
        return !(result.length == 1 && result[0] == 0);
    }
}
