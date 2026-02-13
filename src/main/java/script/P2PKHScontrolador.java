package script;

import java.util.ArrayList;
import java.util.List;

public class P2PKHScontrolador{

    public static List<Instruction> build(byte[] firma, byte[] pubKey) {

        List<Instruction> script = new ArrayList<>();

        script.add(new Instruction(firma));
        script.add(new Instruction(pubKey));
        script.add(new Instruction(Opcode.OP_DUP));
        script.add(new Instruction(Opcode.OP_HASH160));
        script.add(new Instruction(CryptoUtils.hash160(pubKey)));
        script.add(new Instruction(Opcode.OP_EQUALVERIFY));
        script.add(new Instruction(Opcode.OP_CHECKSIG));

        return script;
    }
}
