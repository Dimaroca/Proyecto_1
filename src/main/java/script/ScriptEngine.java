package script;
import java.util.List;

public interface ScriptEngine {
    boolean execute(List<Instruction> script);
}