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
     * Convierte una línea con números separados por coma
     * en un arreglo de bytes.
     *
     * @param line línea a convertir
     * @return arreglo de bytes correspondiente
     * @throws IOException si el formato es inválido
     */
    private byte[] parseLine(String line) throws IOException {

        String[] parts = line.trim().split(",");

        byte[] data = new byte[parts.length];

        try {
            for (int i = 0; i < parts.length; i++) {
                data[i] = Byte.parseByte(parts[i].trim());
            }
        } catch (NumberFormatException e) {
            throw new IOException("Formato inválido en el archivo: " + line);
        }

        return data;
    }
}
