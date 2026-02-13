package script;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        byte[] firma = new byte[]{1,2,3};
        byte[] pubKey = new byte[]{1,2,3};
        
        List<Instruction> script = P2PKHScontrolador.build(firma, pubKey);
        ScriptEngine interpreter = new Interpreter(true);
        
        boolean result = interpreter.execute(script);
        System.out.println("Resultado: " + result);

    }
}
