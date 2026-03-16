package script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de leer archivos de texto utilizados
 * por el intérprete de scripts.
 *
 * Puede leer:
 * 1) Firma y clave pública, para P2PKH
 * 2) Scripts completos, uno por línea
 */
public class txtManager {

    /**
     * Lee un archivo de texto y retorna la firma y la clave pública
     * como arreglos de bytes.
     */
    public byte[][] readSignatureAndPubKey(String filePath) throws IOException {

        List<String> lines = Files.readAllLines(Path.of(filePath));

        if (lines.size() < 2) {
            throw new IOException("El archivo debe contener al menos dos líneas");
        }

        byte[] signature = parseLine(lines.get(0));
        byte[] pubKey = parseLine(lines.get(1));

        return new byte[][] { signature, pubKey };
    }

    /**
     * Lee un archivo donde cada línea representa un script completo.
     */
    public List<String> readScripts(String filePath) throws IOException {

        List<String> lines = Files.readAllLines(Path.of(filePath));
        List<String> scripts = new ArrayList<>();

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("#")) {
                continue;
            }

            scripts.add(line);
        }

        return scripts;
    }

    /**
     * Convierte una línea en bytes
     */
    private byte[] parseLine(String line) {
        return line.trim().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Convierte una línea de texto en una lista de instrucciones
     * que el intérprete puede ejecutar.
     */
    public List<Instruction> parseScript(String line) {

        List<Instruction> script = new ArrayList<>();

        // soporta espacios, comas o punto y coma como separadores
        String[] tokens = line.trim().split("[,;\\s]+");

        for (String token : tokens) {

            token = token.trim();

            if (token.isEmpty()) {
                continue;
            }

            // intentar interpretar como opcode
            try {
                Opcode op = Opcode.valueOf(token.toUpperCase());
                script.add(new Instruction(op));
                continue;
            } catch (IllegalArgumentException ignored) {}

            // booleanos
            if (token.equalsIgnoreCase("true")) {
                script.add(new Instruction(new byte[]{1}));
                continue;
            }

            if (token.equalsIgnoreCase("false")) {
                script.add(new Instruction(new byte[]{0}));
                continue;
            }

            // números
            try {
                int number = Integer.parseInt(token);
                script.add(new Instruction(new byte[]{(byte) number}));
                continue;
            } catch (NumberFormatException ignored) {}

            // si no es nada de lo anterior, se trata como DATA
            script.add(new Instruction(token.getBytes(StandardCharsets.UTF_8)));
        }

        return script;
    }
}