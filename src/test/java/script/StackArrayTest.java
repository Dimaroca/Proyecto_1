package script;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class StackArrayTest {

    @Test
    void stackTest() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.peek());
        assertEquals(20, stack.pop());  
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void scriptOP1Test() {
        Interpreter interpreter = new Interpreter();
        boolean result = interpreter.execute(List.of(Opcode.OP_1));
        assertTrue(result);
    }

    @Test
    void scriptOP0Test() {
        Interpreter interpreter = new Interpreter();
        boolean result = interpreter.execute(List.of(Opcode.OP_0));
        assertFalse(result);
    }

    @Test
    void equalVerifyTest() {
        Interpreter interpreter = new Interpreter();

        boolean result = interpreter.execute(List.of(
                Opcode.OP_2,
                Opcode.OP_DUP,
                Opcode.OP_EQUALVERIFY,
                Opcode.OP_1
        ));

        assertTrue(result);
    }

}
