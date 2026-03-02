package script;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        txtManager reader = new txtManager();

        byte[][] inputs = reader.readSignatureAndPubKey("prueba.txt");//aqui es donde iria el archivo .txt
        
        byte[] pubKey = inputs[1];
        byte[] firma = new byte[]{1,2,4};
        
        List<Instruction> script = P2PKHScontrolador.build(firma, pubKey);
        
        ScriptEngine interpreter = new Interpreter(true);
        
        boolean result = interpreter.execute(script);
        
        System.out.println("Resultado del script: " + result);

    }
}
