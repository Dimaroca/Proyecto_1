package script;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InstructionTest {
    Instruction instruction = new Instruction(Opcode.OP_CHECKSIG);
    
    @Test
    void testGetType() {
        assertEquals(Instruction.Type.OPCODE, instruction.getType());
    }

    @Test
    void testGetOpCode() {
        assertEquals(Opcode.OP_CHECKSIG, instruction.getOpCode());
    }

    @Test
    void testGetData() {
        assertNull(instruction.getData());
    }
}
