package script;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void opDropTest() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                DATA(new byte[]{1}),
                DATA(new byte[]{2}),
                OP(Opcode.OP_DROP)
        )));
    }

    @Test
    void opDropOnEmptyStackTest() {
        Interpreter it = new Interpreter(false);
        assertFalse(it.execute(List.of(
                OP(Opcode.OP_DROP)
        )));
    }

    @Test
    void opEqualTrueTest() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                DATA(new byte[]{7}),
                DATA(new byte[]{7}),
                OP(Opcode.OP_EQUAL)
        )));
    }

    @Test
    void opEqualFalseTest() {
        Interpreter it = new Interpreter(false);

        assertFalse(it.execute(List.of(
                DATA(new byte[]{7}),
                DATA(new byte[]{8}),
                OP(Opcode.OP_EQUAL)
        )));
    }

    @Test
    void opEqualWithInsufficientElementsTest() {
        Interpreter it = new Interpreter(false);

        assertFalse(it.execute(List.of(
                DATA(new byte[]{7}),
                OP(Opcode.OP_EQUAL)
        )));
    }

    @Test
    void opHash160Test() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                DATA("abc".getBytes()),
                OP(Opcode.OP_HASH160)
        )));
    }

    @Test
    void opHash160OnEmptyStackTest() {
        Interpreter it = new Interpreter(false);

        assertFalse(it.execute(List.of(
                OP(Opcode.OP_HASH160)
        )));
    }

    @Test
    void opCheckSigTrueTest() {
        Interpreter it = new Interpreter(false);

        byte[] firma = "abc".getBytes();

        assertTrue(it.execute(List.of(
                DATA(firma),
                DATA(firma),
                OP(Opcode.OP_CHECKSIG)
        )));
    }

    @Test
    void opCheckSigFalseTest() {
        Interpreter it = new Interpreter(false);

        assertFalse(it.execute(List.of(
                DATA("abc".getBytes()),
                DATA("xyz".getBytes()),
                OP(Opcode.OP_CHECKSIG)
        )));
    }

    @Test
    void opCheckSigWithInsufficientElementsTest() {
        Interpreter it = new Interpreter(false);

        assertFalse(it.execute(List.of(
                DATA("abc".getBytes()),
                OP(Opcode.OP_CHECKSIG)
        )));
    }
    @Test
    void opIfTrueTest() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                OP(Opcode.OP_1),
                OP(Opcode.OP_IF),
                OP(Opcode.OP_9),
                OP(Opcode.OP_ENDIF)
        )));
    }

    @Test
    void opIfFalseTest() {
        Interpreter it = new Interpreter(false);

        assertFalse(it.execute(List.of(
                OP(Opcode.OP_0),
                OP(Opcode.OP_IF),
                OP(Opcode.OP_9),
                OP(Opcode.OP_ENDIF)
        )));
    }

    @Test
    void opIfElseTrueBranchTest() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                OP(Opcode.OP_1),
                OP(Opcode.OP_IF),
                OP(Opcode.OP_9),
                OP(Opcode.OP_ELSE),
                OP(Opcode.OP_8),
                OP(Opcode.OP_ENDIF)
        )));
    }

    @Test
    void opIfElseFalseBranchTest() {
        Interpreter it = new Interpreter(false);

        assertTrue(it.execute(List.of(
                OP(Opcode.OP_0),
                OP(Opcode.OP_IF),
                OP(Opcode.OP_9),
                OP(Opcode.OP_ELSE),
                OP(Opcode.OP_8),
                OP(Opcode.OP_ENDIF)
        )));
    }
}
