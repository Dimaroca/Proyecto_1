package script;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        txtManager reader = new txtManager();
        //se espera que se ingrese un archivo .txt que tenga en la primera linea la firma y en la segunda
        //el pubkey, los cuales no estén separados por espacio y se separen por una coma. Ej:
        //1,2,3,4 --> firma
        //1,2,4,5 --> pubKey
        byte[][] inputs = reader.readSignatureAndPubKey("prueba.txt");//aqui es donde iria el archivo .txt
        
        byte[] firma = inputs[0];
        byte[] pubKey = inputs[1];
        
        List<Instruction> script = P2PKHScontrolador.build(firma, pubKey);
        
        ScriptEngine interpreter = new Interpreter(true);
        
        boolean result = interpreter.execute(script);
        
        System.out.println("Resultado del script: " + result);

    }
}
