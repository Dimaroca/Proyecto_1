package script;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CryptoUtilsTest {
    
    @Test
    void hash160Test() {
        byte[] data = "test".getBytes();
        byte[] hash = CryptoUtils.hash160(data);
        assertNotNull(hash);
        assertEquals(20, hash.length); // RIPEMD-160 produces a 20-byte hash
    }

    @Test
    void checkSigTest() {
        byte[] signature = "signature".getBytes();
        byte[] pubKey = "signature".getBytes();
        assertTrue(CryptoUtils.checkSig(signature, pubKey));

        byte[] wrongPubKey = "wrong".getBytes();
        assertFalse(CryptoUtils.checkSig(signature, wrongPubKey));
    }
}
