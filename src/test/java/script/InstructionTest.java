package script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    @Test
    void testDataInstructionType() {
        Instruction instruction = new Instruction(new byte[]{1, 2, 3});
        assertEquals(Instruction.Type.DATA, instruction.getType());
    }

    @Test
    void testDataInstructionOpcodeIsNull() {
        Instruction instruction = new Instruction(new byte[]{1, 2, 3});
        assertNull(instruction.getOpCode());
    }

    @Test
    void testDataInstructionReturnsBytes() {
        byte[] data = new byte[]{1, 2, 3};
        Instruction instruction = new Instruction(data);
        assertArrayEquals(data, instruction.getData());
    }
}