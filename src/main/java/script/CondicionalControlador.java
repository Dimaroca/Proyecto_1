package script;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilizada para construir scripts de prueba que demuestran
 * el funcionamiento de las instrucciones de control de flujo
 * OP_IF, OP_ELSE y OP_ENDIF.
 * 
 * 0 representa false
 * 1 representa true
 */
public class CondicionalControlador {

    /**
     * Construye un script donde la condición es verdadera.
     * 
     * Script equivalente:
     * 
     * Como la condición es 1, se ejecuta el IF
     * y se empuja "hola" a la pila.
     * 
     * @return lista de instrucciones que representan el script
     */
    public static List<Instruction> buildTrueCase() {

        List<Instruction> script = new ArrayList<>();

        // condición verdadera
        script.add(new Instruction(new byte[]{1}));

        script.add(new Instruction(Opcode.OP_IF));
        script.add(new Instruction("hola".getBytes()));

        script.add(new Instruction(Opcode.OP_ELSE));
        script.add(new Instruction("adios".getBytes()));

        script.add(new Instruction(Opcode.OP_ENDIF));

        return script;
    }

    /**
     * Construye un script donde la condición es falsa.
     * 
     * Como la condición es 0, el bloque IF no se ejecuta
     * y se ejecuta el ELSE, empujando "adios" a la pila.
     * 
     * @return lista de instrucciones que representan el script
     */
    public static List<Instruction> buildFalseCase() {

        List<Instruction> script = new ArrayList<>();

        // condición falsa
        script.add(new Instruction(new byte[]{0}));

        script.add(new Instruction(Opcode.OP_IF));
        script.add(new Instruction("hola".getBytes()));

        script.add(new Instruction(Opcode.OP_ELSE));
        script.add(new Instruction("adios".getBytes()));

        script.add(new Instruction(Opcode.OP_ENDIF));

        return script;
    }
}