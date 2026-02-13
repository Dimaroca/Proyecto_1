package script;

/**
 * Representa una instrucción individual dentro de un script.
 *
 * <p>Una instrucción puede ser de dos tipos:</p>
 * <ul>
 *   <li>{@code OPCODE}: representa una operación a ejecutar.</li>
 *   <li>{@code DATA}: representa un arreglo de bytes que debe
 *       insertarse en la pila.</li>
 * </ul>
 */
public class Instruction {

    /**
     * Enum que define el tipo de instrucción.
     */
    public enum Type {
        /** Representa una operación del lenguaje */
        OPCODE,

        /** Representa datos que serán empujados a la pila */
        DATA
    }

    private final Type type;
    private final Opcode opCode;
    private final byte[] data;

    /**
     * Construye una instrucción de tipo OPCODE.
     *
     * @param opCode operación a ejecutar
     * @pre opCode != null
     * @post la instrucción será de tipo OPCODE
     */
    public Instruction(Opcode opCode) {
        this.type = Type.OPCODE;
        this.opCode = opCode;
        this.data = null;
    }

    /**
     * Construye una instrucción de tipo DATA.
     *
     * @param data arreglo de bytes a insertar en la pila
     * @pre data != null
     * @post la instrucción será de tipo DATA
     */
    public Instruction(byte[] data) {
        this.type = Type.DATA;
        this.data = data;
        this.opCode = null;
    }

    /**
     * Retorna el tipo de la instrucción.
     *
     * @return {@link Type#OPCODE} o {@link Type#DATA}
     */
    public Type getType() {
        return type;
    }

    /**
     * Retorna el opcode asociado a la instrucción.
     *
     * @return opcode correspondiente
     * @pre el tipo debe ser OPCODE
     */
    public Opcode getOpCode() {
        return opCode;
    }

    /**
     * Retorna los datos asociados a la instrucción.
     *
     * @return arreglo de bytes
     * @pre el tipo debe ser DATA
     */
    public byte[] getData() {
        return data;
    }
}