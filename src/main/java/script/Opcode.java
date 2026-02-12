package script;

public interface Opcode {
    
    void execute(StackManager stack, StackValidator validator);
    
}
