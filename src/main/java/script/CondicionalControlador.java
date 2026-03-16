package script;

import java.util.ArrayList;
import java.util.List;

public class CondicionalControlador {
    //0 es false y 1 es true
    public static List<Instruction> buildTrueCase() {

        List<Instruction> script = new ArrayList<>();

        script.add(new Instruction(new byte[]{1}));

        script.add(new Instruction(Opcode.OP_IF));
        script.add(new Instruction("hola".getBytes()));

        script.add(new Instruction(Opcode.OP_ELSE));
        script.add(new Instruction("adios".getBytes()));

        script.add(new Instruction(Opcode.OP_ENDIF));

        return script;
    }

    public static List<Instruction> buildFalseCase() {

        List<Instruction> script = new ArrayList<>();

        script.add(new Instruction(new byte[]{0}));

        script.add(new Instruction(Opcode.OP_IF));
        script.add(new Instruction("hola".getBytes()));

        script.add(new Instruction(Opcode.OP_ELSE));
        script.add(new Instruction("adios".getBytes()));

        script.add(new Instruction(Opcode.OP_ENDIF));

        return script;
    }
}