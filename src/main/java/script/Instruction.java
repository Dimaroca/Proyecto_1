package script;

public class Instruction{

    public enum Type {
        OPCODE,
        DATA
    }

    private final Type type;
    private final Opcode opCode;
    private final byte[] data;

    public Instruction(Opcode opCode) {
        this.type = Type.OPCODE;
        this.opCode = opCode;
        this.data = null;
    }

    public Instruction(byte[] data) {
        this.type = Type.DATA;
        this.data = data;
        this.opCode = null;
    }

    public Type getType() {
        return type;
    }

    public Opcode getOpCode() {
        return opCode;
    }

    public byte[] getData() {
        return data;
    }
}
