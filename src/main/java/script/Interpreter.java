package script;

import java.util.Arrays;
import java.util.List;

/**
 * Implementación concreta de {@link ScriptEngine} que interpreta
 * un subconjunto de instrucciones de Bitcoin Script.
 *
 * <p>Un script se considera válido si:</p>
 * <ul>
 *   <li>No ocurre ningún error durante su ejecución.</li>
 *   <li>La pila no queda vacía al finalizar.</li>
 *   <li>El elemento superior de la pila representa un valor verdadero.</li>
 * </ul>
 *
 * <p>Complejidad temporal: O(n), donde n es el número de instrucciones
 * del script.</p>
 */
public class Interpreter implements ScriptEngine {

    private StackADT<byte[]> stack;
    private boolean trace;

    /**
     * Construye un interpreter sin trace
     */
    public Interpreter() {
        this(false);
    }

    /**
     * Construye un interpreter.
     *
     * @param trace si es true, imprime el estado de la pila
     *              después de cada instrucción
     */
    public Interpreter(boolean trace) {
        this.stack = new ArrayStack<>();
        this.trace = trace;
    }

    /**
     * Ejecuta un script compuesto por una lista de instrucciones.
     *
     * @param script lista ordenada de instrucciones a ejecutar
     * @return true si el script es válido, false en caso contrario
     *
     * @pre script != null
     * @post la pila se evalúa según las reglas de validación
     * @complexity O(n)
     */
    @Override
    public boolean execute(List<Instruction> script) {

        stack = new ArrayStack<>();

        try {
            for (Instruction instruction : script) {
                executeInstruction(instruction);

                if (trace) {
                    System.out.println("Stack: " + stack);
                }
            }

            if (stack.isEmpty()) {
                return false;
            }

            byte[] result = stack.pop();
            return !isZero(result);

        } catch (ScriptException e) {
            return false;
        }
    }
    /**
    * Ejecuta una instrucción individual del script.
    *
    * @param instruction instrucción a ejecutar
    * @throws ScriptException si ocurre un error durante la ejecución
    */
    private void executeInstruction(Instruction instruction) {

        if (instruction.getType() == Instruction.Type.DATA) {
            stack.push(instruction.getData());
            return;
        }

        Opcode op = instruction.getOpCode();

        switch (op) {

            case OP_0:  stack.push(new byte[]{0});  break;
            case OP_1:  stack.push(new byte[]{1});  break;
            case OP_2:  stack.push(new byte[]{2});  break;
            case OP_3:  stack.push(new byte[]{3});  break;
            case OP_4:  stack.push(new byte[]{4});  break;
            case OP_5:  stack.push(new byte[]{5});  break;
            case OP_6:  stack.push(new byte[]{6});  break;
            case OP_7:  stack.push(new byte[]{7});  break;
            case OP_8:  stack.push(new byte[]{8});  break;
            case OP_9:  stack.push(new byte[]{9});  break;
            case OP_10: stack.push(new byte[]{10}); break;
            case OP_11: stack.push(new byte[]{11}); break;
            case OP_12: stack.push(new byte[]{12}); break;
            case OP_13: stack.push(new byte[]{13}); break;
            case OP_14: stack.push(new byte[]{14}); break;
            case OP_15: stack.push(new byte[]{15}); break;
            case OP_16: stack.push(new byte[]{16}); break;

            case OP_DUP:
                executeDup();
                break;

            case OP_DROP:
                executeDrop();
                break;

            case OP_EQUAL:
                executeEqual();
                break;

            case OP_EQUALVERIFY:
                executeEqualVerify();
                break;

            case OP_HASH160:
                executeHash160();
                break;

            case OP_CHECKSIG:
                executeCheckSig();
                break;

            default:
                throw new ScriptException("Opcode no soportado: " + op);
        }
    }
    /**
    * Duplica el elemento superior de la pila.
    *
    * @throws ScriptException si la pila está vacía
    */
    private void executeDup() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío en OP_DUP");
        }
        byte[] top = stack.peek();
        stack.push(top);
    }
    /**
    * Elimina el elemento superior de la pila.
    *
    * @throws ScriptException si la pila está vacía
    *
    * @post el tamaño de la pila disminuye en uno
    * @complexity O(1)
    */
    private void executeDrop() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío en OP_DROP");
        }
        stack.pop();
    }
    /**
    * Compara los dos elementos superiores de la pila.
    * Empuja 1 si son iguales, 0 en caso contrario.
    *
    * @throws ScriptException si hay menos de dos elementos
    */
    private void executeEqual() {
        if (stack.size() < 2) {
            throw new ScriptException("No hay suficientes elementos para OP_EQUAL");
        }

        byte[] a = stack.pop();
        byte[] b = stack.pop();

        boolean equal = Arrays.equals(a, b);
        stack.push(booleanToBytes(equal));
    }
    /**
    * Verifica que los dos elementos superiores sean iguales.
    * Si no lo son, detiene la ejecución lanzando una excepción.
    *
    * @throws ScriptException si la comparación falla
    */
    private void executeEqualVerify() {
        executeEqual();

        byte[] result = stack.pop();

        if (isZero(result)) {
            throw new ScriptException("OP_EQUALVERIFY falló");
        }
    }
    /**
    * Aplica la función HASH160 al elemento superior de la pila.
    *
    * @throws ScriptException si la pila está vacía
    */
    private void executeHash160() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío en OP_HASH160");
        }

        byte[] data = stack.pop();
        byte[] hash = CryptoUtils.hash160(data);
        stack.push(hash);
    }
    /**
    * Verifica la firma digital (simulada).
    * Empuja 1 si es válida, 0 en caso contrario.
    *
    * @throws ScriptException si hay menos de dos elementos
    */
    private void executeCheckSig() {
        if (stack.size() < 2) {
            throw new ScriptException("No hay suficientes elementos para OP_CHECKSIG");
        }

        byte[] pubKey = stack.pop();
        byte[] signature = stack.pop();

        boolean valid = CryptoUtils.checkSig(signature, pubKey);
        stack.push(booleanToBytes(valid));
    }
    /**
    * Determina si un arreglo de bytes representa el valor falso.
    *
    * Un valor se considera falso si todos sus bytes son iguales a 0. 
    * Cualquier otro valor se considera verdadero.</p>
    *
    * @param data arreglo de bytes a evaluar
    * @return true si el valor representa 0, false en caso contrario
    * @complexity O(n), donde n es el tamaño del arreglo
    */
    private boolean isZero(byte[] data) {
        for (byte b : data) {
            if (b != 0) return false;
        }
        return true;
    }
    /**
    * Convierte un valor booleano en su representación binaria
    * para almacenarlo en la pila.
    *
    *
    * @param value valor boolean a convertir
    * @return arreglo de bytes
    * @complexity O(1)
    */
    private byte[] booleanToBytes(boolean value) {
        return value ? new byte[]{1} : new byte[]{0};
    }
}