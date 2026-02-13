package script;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Clase con operaciones criptográficas
 * simuladas utilizadas por el interpreter.
 * 
 */
public final class CryptoUtils {

    /**
     * Constructor privado para evitar la instanciación.
     */
    private CryptoUtils() {
    }

    /**
     * Aplica una función HASH160 simulada a los datos recibidos.
     *
     * @param data arreglo de bytes a procesar
     * @return hash resultante
     * @throws ScriptException si el algoritmo de hash no está disponible
     * @complexity O(n), donde n es el tamaño del arreglo de entrada
     */
    public static byte[] hash160(byte[] data) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] shaHash = sha256.digest(data);

            MessageDigest ripemd160 = MessageDigest.getInstance("SHA-1");

            return ripemd160.digest(shaHash);

        } catch (NoSuchAlgorithmException e) {
            throw new ScriptException("Algoritmo de hash no disponible", e);
        }
    }

    /**
     * Verifica una firma digital.
     *
     * <p>La firma se considera válida
     * únicamente si es igual a la clave pública proporcionada.
     * Esta verificación no representa criptografía real.</p>
     *
     * @param signature firma digital simulada
     * @param pubKey clave pública simulada
     * @return true si la firma es válida, false en caso contrario
     * @complexity O(n), donde n es el tamaño del arreglo comparado
     */
    public static boolean checkSig(byte[] signature, byte[] pubKey) {
        return Arrays.equals(signature, pubKey);
    }
}
