package script;

import static org.junit.jupiter.api.Assertions.*;
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
}
