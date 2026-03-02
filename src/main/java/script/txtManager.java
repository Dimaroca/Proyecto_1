package script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Clase encargada de leer archivos de texto
 * que contienen la firma y la clave pública.
 *
 * <p>Se espera que el archivo tenga el siguiente formato:</p>
 *
 * <pre>
 * 1,2,3
 * 1,2,3
 * </pre>
 *
 * Donde:
 * <ul>
 *   <li>Primera línea: firma</li>
 *   <li>Segunda línea: clave pública</li>
 * </ul>
 */
public class txtManager {

    /**
     * Lee un archivo de texto y retorna la firma y la clave pública
     * como arreglos de bytes.
     *
     * @param filePath ruta del archivo a leer
     * @return arreglo bidimensional donde:
     *         [0] = firma,
     *         [1] = clave pública
     * @throws IOException si el archivo no existe o el formato es inválido
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
     * Convierte el input en bytes, parecido a lo que hace Bitcoin
     *
     */
    private byte[] parseLine(String line) {
        return line.trim().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}
