package script;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

/**
 * Esta clase interpreta y ejecuta una lista de instrucciones tipo Script.
 * Usa una pila para ir manipulando datos y otra pila para manejar bloques
 * condicionales como IF/ELSE. Implementa la interfaz ScriptEngine.
 */
public class Interpreter implements ScriptEngine {

    /** Pila principal donde se guardan los valores en bytes. */
    private StackADT<byte[]> stack;

    /** Si es true, muestra en consola cada operación ejecutada. */
    private boolean trace;

    /** Pila que lleva el control de si se está ejecutando dentro de bloques IF. */
    private StackADT<Boolean> stackDeEjecucion;

    public Interpreter() {
        this(false);
    }

    /**
     * Constructor que permite decidir si se quiere activar el modo de trace.
     * @param trace si se quiere imprimir cada instrucción ejecutada.
     */
    public Interpreter(boolean trace) {
        this.stack = new ArrayStack<>();
        this.trace = trace;
        this.stackDeEjecucion = new ArrayStack<>();
    }

    /**
     * Ejecuta una lista de instrucciones tipo Script.
     * @param script lista de instrucciones.
     * @return true si el resultado final no es cero.
     */
    @Override
    public boolean execute(List<Instruction> script) {

        stack = new ArrayStack<>();
        stackDeEjecucion = new ArrayStack<>();

        try {
            for (Instruction instruction : script) {

                executeInstruction(instruction);

                if (trace) {
                    String op;
                    if (instruction.getType() == Instruction.Type.OPCODE) {
                        op = instruction.getOpCode().toString();
                    } else {
                        op = "DATA";
                    }
                    System.out.println("Ejecutando: " + op + " | Pila: " + pilaATexto());
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
     * Ejecuta una sola instrucción dependiendo de su tipo u opcode.
     * @param instruction instrucción a procesar.
     */
    private void executeInstruction(Instruction instruction) {

        if (!seEstaEjecutando()) {
            if (instruction.getType() == Instruction.Type.OPCODE) {
                Opcode op = instruction.getOpCode();
                if (op != Opcode.OP_IF && op != Opcode.OP_ELSE && op != Opcode.OP_ENDIF) {
                    return;
                }
            } else {
                return;
            }
        }

        if (instruction.getType() == Instruction.Type.DATA) {
            stack.push(instruction.getData());
            return;
        }

        Opcode op = instruction.getOpCode();

        switch (op) {

            case OP_0: stack.push(new byte[]{0}); break;
            case OP_1: stack.push(new byte[]{1}); break;
            case OP_2: stack.push(new byte[]{2}); break;
            case OP_3: stack.push(new byte[]{3}); break;
            case OP_4: stack.push(new byte[]{4}); break;
            case OP_5: stack.push(new byte[]{5}); break;
            case OP_6: stack.push(new byte[]{6}); break;
            case OP_7: stack.push(new byte[]{7}); break;
            case OP_8: stack.push(new byte[]{8}); break;
            case OP_9: stack.push(new byte[]{9}); break;
            case OP_10: stack.push(new byte[]{10}); break;
            case OP_11: stack.push(new byte[]{11}); break;
            case OP_12: stack.push(new byte[]{12}); break;
            case OP_13: stack.push(new byte[]{13}); break;
            case OP_14: stack.push(new byte[]{14}); break;
            case OP_15: stack.push(new byte[]{15}); break;
            case OP_16: stack.push(new byte[]{16}); break;

            case OP_DUP: executeDup(); break;
            case OP_DROP: executeDrop(); break;
            case OP_SWAP: executeSwap(); break;
            case OP_OVER: executeOver(); break;

            case OP_EQUAL: executeEqual(); break;
            case OP_EQUALVERIFY: executeEqualVerify(); break;

            case OP_NOT: executeNot(); break;
            case OP_BOOLAND: executeBoolAnd(); break;
            case OP_BOOLOR: executeBoolOr(); break;

            case OP_ADD: executeAdd(); break;
            case OP_SUB: executeSub(); break;
            case OP_NUMEQUALVERIFY: executeNumEqualVerify(); break;
            case OP_LESSTHAN: executeLessThan(); break;
            case OP_GREATERTHAN: executeGreaterThan(); break;
            case OP_LESSTHANOREQUAL: executeLessOrEqual(); break;
            case OP_GREATERTHANOREQUAL: executeGreaterOrEqual(); break;

            case OP_HASH160: executeHash160(); break;
            case OP_SHA256: executeSHA256(); break;
            case OP_HASH256: executeHASH256(); break;

            case OP_CHECKSIG: executeCheckSig(); break;
            case OP_CHECKSIGVERIFY: executeCheckSigVerify(); break;

            case OP_IF: executeIf(); break;
            case OP_NOTIF: executeNotIf(); break;
            case OP_ELSE: executeElse(); break;
            case OP_ENDIF: executeEndIf(); break;

            case OP_VERIFY: executeVerify(); break;
            case OP_RETURN: throw new ScriptException("OP_RETURN ejecutado");

            default:
                throw new ScriptException("Opcode no soportado: " + op);
        }
    }

    /**
     * Extrae un entero desde la pila
     * @return valor como entero.
     */
    private int popInt() {
        if(stack.isEmpty()){
            throw new ScriptException("Stack vacío");
        }
        return stack.pop()[0];
    }

    /**
     * Inserta un número entero a la pila, convertido a un solo byte.
     * @param value entero a convertir.
     */
    private void pushInt(int value){
        stack.push(new byte[]{(byte)value});
    }

    /**
     * Intercambia los dos elementos superiores de la pila.
     */
    private void executeSwap() {
        if(stack.size()<2) throw new ScriptException("OP_SWAP requiere 2 elementos");
        byte[] a = stack.pop();
        byte[] b = stack.pop();
        stack.push(a);
        stack.push(b);
    }

    /**
     * Copia el segundo elemento de la pila al tope.
     */
    private void executeOver() {
        if(stack.size()<2) throw new ScriptException("OP_OVER requiere 2 elementos");
        byte[] a = stack.pop();
        byte[] b = stack.peek();
        stack.push(a);
        stack.push(Arrays.copyOf(b,b.length));
    }

    /**
     * Aplica la operación NOT (si es 0 → 1, si no → 0).
     */
    private void executeNot(){
        int v = popInt();
        pushInt(v==0 ? 1 : 0);
    }

    /**
     * AND lógico entre los dos valores superiores de la pila.
     */
    private void executeBoolAnd(){
        int b = popInt();
        int a = popInt();
        pushInt((a!=0 && b!=0) ? 1:0);
    }

    /**
     * OR lógico entre los dos valores superiores de la pila.
     */
    private void executeBoolOr(){
        int b = popInt();
        int a = popInt();
        pushInt((a!=0 || b!=0) ? 1:0);
    }

    /**
     * Suma los dos valores superiores de la pila.
     */
    private void executeAdd(){
        int b = popInt();
        int a = popInt();
        pushInt(a+b);
    }

    /**
     * Resta los dos valores superiores de la pila.
     */
    private void executeSub(){
        int b = popInt();
        int a = popInt();
        pushInt(a-b);
    }

    /**
     * Verifica si dos valores son iguales, si no lanza error.
     */
    private void executeNumEqualVerify(){
        int b = popInt();
        int a = popInt();
        if(a!=b) throw new ScriptException("NUMEQUALVERIFY falló");
    }

    /**
     * Compara si a < b.
     */
    private void executeLessThan(){
        int b = popInt();
        int a = popInt();
        pushInt(a<b ? 1:0);
    }

    /**
     * Compara si a > b.
     */
    private void executeGreaterThan(){
        int b = popInt();
        int a = popInt();
        pushInt(a>b ? 1:0);
    }

    /**
     * Compara si a <= b.
     */
    private void executeLessOrEqual(){
        int b = popInt();
        int a = popInt();
        pushInt(a<=b ? 1:0);
    }

    /**
     * Compara si a >= b.
     */
    private void executeGreaterOrEqual(){
        int b = popInt();
        int a = popInt();
        pushInt(a>=b ? 1:0);
    }

    /**
     * Aplica SHA-256 al elemento superior de la pila.
     */
    private void executeSHA256(){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            stack.push(digest.digest(stack.pop()));
        }catch(Exception e){
            throw new ScriptException("SHA256 error");
        }
    }

    /**
     * Aplica SHA-256 dos veces seguidas al dato superior de la pila.
     */
    private void executeHASH256(){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] first = digest.digest(stack.pop());
            stack.push(digest.digest(first));
        }catch(Exception e){
            throw new ScriptException("HASH256 error");
        }
    }

    /**
     * Verifica firma y luego llama a VERIFY para validar.
     */
    private void executeCheckSigVerify(){
        executeCheckSig();
        executeVerify();
    }

    /**
     * Verifica que un valor booleano no sea 0, si es 0 lanza error.
     */
    private void executeVerify(){
        if(stack.isEmpty()) throw new ScriptException("VERIFY sin valor");
        if(isZero(stack.pop())) throw new ScriptException("VERIFY falló");
    }

    /**
     * Maneja la instrucción NOTIF para bloques condicionales.
     */
    private void executeNotIf(){
        if(!seEstaEjecutando()){
            stackDeEjecucion.push(false);
            return;
        }
        if(stack.isEmpty()) throw new ScriptException("Pila vacía en OP_NOTIF");
        byte[] condicion = stack.pop();
        stackDeEjecucion.push(isZero(condicion));
    }

    /**
     * Duplica el elemento superior de la pila.
     */
    private void executeDup(){
        if(stack.isEmpty()) throw new ScriptException("Stack vacío en OP_DUP");
        byte[] top = stack.peek();
        stack.push(Arrays.copyOf(top, top.length));
    }

    /**
     * Quita el elemento superior de la pila.
     */
    private void executeDrop(){
        if(stack.isEmpty()) throw new ScriptException("Stack vacío en OP_DROP");
        stack.pop();
    }

    /**
     * Compara si dos arrays de bytes son iguales.
     */
    private void executeEqual(){
        if(stack.size()<2) throw new ScriptException("OP_EQUAL requiere 2 elementos");
        byte[] a = stack.pop();
        byte[] b = stack.pop();
        stack.push(booleanToBytes(Arrays.equals(a,b)));
    }

    /**
     * Igual que OP_EQUAL pero lanza error si no son iguales.
     */
    private void executeEqualVerify(){
        executeEqual();
        if(isZero(stack.pop())) throw new ScriptException("OP_EQUALVERIFY falló");
    }

    /**
     * Aplica HASH160 a los datos superiores de la pila.
     */
    private void executeHash160(){
        if(stack.isEmpty()) throw new ScriptException("Stack vacío en OP_HASH160");
        stack.push(CryptoUtils.hash160(stack.pop()));
    }

    /**
     * Verifica una firma digital con una llave pública.
     */
    private void executeCheckSig(){
        if(stack.size()<2) throw new ScriptException("OP_CHECKSIG requiere 2 elementos");
        byte[] pubKey = stack.pop();
        byte[] signature = stack.pop();
        boolean valid = CryptoUtils.checkSig(signature,pubKey);
        stack.push(booleanToBytes(valid));
    }

    /**
     * Maneja el inicio de un bloque IF.
     */
    private void executeIf(){
        if(!seEstaEjecutando()){
            stackDeEjecucion.push(false);
            return;
        }
        if(stack.isEmpty()) throw new ScriptException("Pila vacía en OP_IF");
        stackDeEjecucion.push(!isZero(stack.pop()));
    }

    /**
     * Cambia entre la rama IF y la rama ELSE.
     */
    private void executeElse(){
        if(stackDeEjecucion.isEmpty()) throw new ScriptException("OP_ELSE sin OP_IF");
        stackDeEjecucion.push(!stackDeEjecucion.pop());
    }

    /**
     * Termina un bloque IF/ELSE.
     */
    private void executeEndIf(){
        if(stackDeEjecucion.isEmpty()) throw new ScriptException("OP_ENDIF sin OP_IF");
        stackDeEjecucion.pop();
    }

    /**
     * Revisa si un arreglo de bytes representa un valor 0.
     * @param data array a revisar
     * @return true si todos los bytes son 0.
     */
    private boolean isZero(byte[] data){
        for(byte b:data){
            if(b!=0) return false;
        }
        return true;
    }

    /**
     * Convierte un boolean a un array con 0 ó 1.
     * @param value booleano
     * @return bytes representando true o false
     */
    private byte[] booleanToBytes(boolean value){
        return value ? new byte[]{1} : new byte[]{0};
    }

    /**
     * Determina si actualmente se deben ejecutar las instrucciones,
     * dependiendo del estado acumulado de los bloques IF.
     * @return true si la ejecución debe continuar.
     */
    private boolean seEstaEjecutando() {
        if (stackDeEjecucion.isEmpty()) {
            return true;
        }
        StackADT<Boolean> temp = new ArrayStack<>();
        boolean ejecutando = true;
        while (!stackDeEjecucion.isEmpty()) {
            boolean b = stackDeEjecucion.pop();
            temp.push(b);
            if (!b) {
                ejecutando = false;
            }
        }
        while (!temp.isEmpty()) {
            stackDeEjecucion.push(temp.pop());
        }
        return ejecutando;
    }

    /**
     * Convierte el contenido actual de la pila en texto para mostrarlo.
     * @return representación en texto de la pila.
     */
    private String pilaATexto(){

        StringBuilder sb = new StringBuilder("[");
        StackADT<byte[]> temp = new ArrayStack<>();

        while(!stack.isEmpty()){

            byte[] valor = stack.pop();
            temp.push(valor);

            boolean printable=true;

            for(byte b:valor){
                if(b<32 || b>126){
                    printable=false;
                    break;
                }
            }

            if(printable){
                sb.append(new String(valor));
            }else{
                StringBuilder hex=new StringBuilder();
                for(byte b:valor){
                    hex.append(String.format("%02x",b));
                }
                sb.append(hex);
            }

            sb.append(", ");
        }

        while(!temp.isEmpty()){
            stack.push(temp.pop());
        }

        if(sb.length()>1){
            sb.setLength(sb.length()-2);
        }

        sb.append("]");

        return sb.toString();
    }
}