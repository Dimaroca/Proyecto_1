package script;

import java.util.List;

/**
 * Interfaz que define el comportamiento de un motor
 * de ejecución de scripts.
 *
 * <p>La ejecución de un script debe:</p>
 * <ul>
 *   <li>Procesar las instrucciones en orden secuencial.</li>
 *   <li>Detenerse si ocurre un error durante la ejecución.</li>
 *   <li>Determinar la validez del script según el estado final de la pila.</li>
 * </ul>
 *
 * <p>Un script se considera válido si:</p>
 * <ul>
 *   <li>No ocurre ninguna excepción durante la ejecución.</li>
 *   <li>La pila final no está vacía.</li>
 *   <li>El elemento superior de la pila representa un valor verdadero (≠ 0).</li>
 * </ul>
 */
public interface ScriptEngine {

    /**
     * Ejecuta una secuencia de instrucciones.
     *
     * @param script lista ordenada de instrucciones a evaluar
     * @return {@code true} si el script es válido,
     *         {@code false} en caso contrario
     *
     * @pre script != null
     * @post el resultado refleja la validez del script según
     *       las reglas de evaluación
     * @complexity O(n), donde n es el número de instrucciones
     */
    boolean execute(List<Instruction> script);
}
