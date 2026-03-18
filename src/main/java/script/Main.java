package script;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        txtManager reader = new txtManager();
        ScriptEngine interpreter = new Interpreter(true);

        demoP2PKH(reader, interpreter);

        System.out.println("\n---------------------------------\n");

        demoCondicional(interpreter);
    }

    private static void demoP2PKH(txtManager reader, ScriptEngine interpreter) throws IOException {

        System.out.println("=== Demostración de P2PKH ===");

        byte[][] inputs = reader.readSignatureAndPubKey("prueba.txt");

        byte[] pubKey = inputs[1];

        // Caso de true
        System.out.println("\nCaso válido:");

        byte[] firmaValida = CryptoUtils.hash160(pubKey);

        List<Instruction> scriptValido = P2PKHScontrolador.build(firmaValida, pubKey);

        boolean resultValido = interpreter.execute(scriptValido);

        System.out.println("Resultado del script: " + resultValido);

        // Caso de false
        System.out.println("\nCaso inválido:");

        byte[] firmaInvalida = "firma incorrecta".getBytes();

        List<Instruction> scriptInvalido = P2PKHScontrolador.build(firmaInvalida, pubKey);

        boolean resultInvalido = interpreter.execute(scriptInvalido);

        System.out.println("Resultado del script: " + resultInvalido);
    }

    private static void demoCondicional(ScriptEngine interpreter) {

        System.out.println("=== Demostración de condicionales ===");

        // Caso de if funcionando
        System.out.println("\nCaso if verdadero:");

        List<Instruction> condicionalTrue = CondicionalControlador.buildTrueCase();

        boolean resultTrue = interpreter.execute(condicionalTrue);

        System.out.println("Resultado del script: " + resultTrue);

        // Caso else funcionando
        System.out.println("\nCaso else ejecutado:");

        List<Instruction> condicionalFalse = CondicionalControlador.buildFalseCase();

        boolean resultFalse = interpreter.execute(condicionalFalse);

        System.out.println("Resultado del script: " + resultFalse);
    }
}
/*
main para adaptar las multiples pruebas con distintos scripts

public static void main(String[] args) throws IOException {

    txtManager reader = new txtManager();
    ScriptEngine interpreter = new Interpreter(true);

    //archivo con los scripts de prueba
    String filePath = "scripts.txt";

    List<String> scripts = reader.readScripts(filePath);

    for (String line : scripts) {

        System.out.println("\n------------------------------");
        System.out.println("Script: " + line);

        List<Instruction> script = reader.parseScript(line);

        boolean result = interpreter.execute(script);

        System.out.println("Resultado: " + result);
    }
}
 */