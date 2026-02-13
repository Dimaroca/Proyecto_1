package script;

/**
 * Enum que representa el conjunto de operaciones
 * soportadas por el intérprete.
 *
 * <p>Cada constante corresponde a una instrucción del
 * subconjunto de Bitcoin Script.</p>
 *
 * <p>Las instrucciones se clasifican en:</p>
 * <ul>
 *   <li>Literales numéricos (OP_0 a OP_16)</li>
 *   <li>Manipulación de pila (OP_DUP, OP_DROP)</li>
 *   <li>Comparación lógica (OP_EQUAL, OP_EQUALVERIFY)</li>
 *   <li>Operaciones criptográficas simuladas (OP_HASH160, OP_CHECKSIG)</li>
 * </ul>
 */
public enum Opcode {
    /** Inserta el valor 0 en la pila */
    OP_0,

    /** Inserta el valor 1 en la pila */
    OP_1,

    /** Inserta el valor 2 en la pila */
    OP_2,

    OP_3,
    OP_4,
    OP_5,
    OP_6,
    OP_7,
    OP_8,
    OP_9,
    OP_10,
    OP_11,
    OP_12,
    OP_13,
    OP_14,
    OP_15,
    OP_16,
    /** Duplica el elemento superior de la pila */
    OP_DUP,

    /** Elimina el elemento superior de la pila */
    OP_DROP,
    /** Compara los dos elementos superiores de la pila */
    OP_EQUAL,

    /**
     * Compara los dos elementos superiores y
     * detiene la ejecución si no son iguales
     */
    OP_EQUALVERIFY,
    /** Aplica la función HASH160 al elemento superior */
    OP_HASH160,

    /**
     * Verifica la firma digital (simulada) usando
     * la clave pública correspondiente
     */
    OP_CHECKSIG
}