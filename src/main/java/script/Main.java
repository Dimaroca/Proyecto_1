package script;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Construimos el script manualmente
        List<Instruction> script = new ArrayList<>();

        // Simulación simple tipo P2PKH mock
        byte[] firma = new byte[]{1, 2, 3};
        byte[] pubKey = new byte[]{1, 2, 3};

        script.add(new Instruction(firma));          // scriptSig: firma
        script.add(new Instruction(pubKey));         // scriptSig: pubKey
        script.add(new Instruction(Opcode.OP_DUP));
        script.add(new Instruction(Opcode.OP_HASH160));
        script.add(new Instruction(CryptoUtils.hash160(pubKey)));
        script.add(new Instruction(Opcode.OP_EQUALVERIFY));
        script.add(new Instruction(Opcode.OP_CHECKSIG));

        // Crear intérprete
        ScriptEngine interpreter = new Interpreter(true);

        boolean result = interpreter.execute(script);

        System.out.println("Resultado del script: " + result);
    }
}
