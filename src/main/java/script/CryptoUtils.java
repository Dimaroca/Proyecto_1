package script;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class CryptoUtils {

    private CryptoUtils() {
    }

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

    public static boolean checkSig(byte[] signature, byte[] pubKey) {
        return Arrays.equals(signature, pubKey);
    }
}