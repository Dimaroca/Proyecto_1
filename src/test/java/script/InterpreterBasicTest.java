package script;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class InterpreterBasicTest {

    private static Instruction OP(Opcode op) {
        return new Instruction(op);
    }

    private static Instruction DATA(byte[] bytes) {
        return new Instruction(bytes);
    }

    @Test
    void op1op0Test() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(OP(Opcode.OP_1))));
        assertFalse(it.execute(List.of(OP(Opcode.OP_0))));
    }

    @Test
    void opDupTest() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                DATA(new byte[]{1}),
                OP(Opcode.OP_DUP)
        )));
    }

    @Test
    void equalVerifyTest() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                DATA(new byte[]{2}),
                DATA(new byte[]{2}),
                OP(Opcode.OP_EQUALVERIFY),
                DATA(new byte[]{1})
        )));

        assertFalse(it.execute(List.of(
                DATA(new byte[]{2}),
                DATA(new byte[]{3}),
                OP(Opcode.OP_EQUALVERIFY)
        )));
    }
}
