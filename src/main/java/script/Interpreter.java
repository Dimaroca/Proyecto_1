package script;

import java.util.Arrays;
import java.util.List;

public class Interpreter implements ScriptEngine {

    private StackADT<byte[]> stack;
    private boolean trace;

    public Interpreter() {
        this(false);
    }

    public Interpreter(boolean trace) {
        this.stack = new ArrayStack<>();
        this.trace = trace;
    }

    @Override
    public boolean execute(List<Instruction> script) {

        // Reiniciar pila en cada ejecución
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

    private void executeInstruction(Instruction instruction) {

        // Si es dato, simplemente se empuja a la pila
        if (instruction.getType() == Instruction.Type.DATA) {
            stack.push(instruction.getData());
            return;
        }

        Opcode op = instruction.getOpCode();

        switch (op) {

            // Literales
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

    private void executeDup() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío en OP_DUP");
        }
        byte[] top = stack.peek();
        stack.push(top);
    }

    private void executeDrop() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío en OP_DROP");
        }
        stack.pop();
    }

    private void executeEqual() {
        if (stack.size() < 2) {
            throw new ScriptException("No hay suficientes elementos para OP_EQUAL");
        }

        byte[] a = stack.pop();
        byte[] b = stack.pop();

        boolean equal = Arrays.equals(a, b);
        stack.push(booleanToBytes(equal));
    }

    private void executeEqualVerify() {
        executeEqual();

        byte[] result = stack.pop();

        if (isZero(result)) {
            throw new ScriptException("OP_EQUALVERIFY falló");
        }
    }

    private void executeHash160() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío en OP_HASH160");
        }

        byte[] data = stack.pop();
        byte[] hash = CryptoUtils.hash160(data);
        stack.push(hash);
    }

    private void executeCheckSig() {
        if (stack.size() < 2) {
            throw new ScriptException("No hay suficientes elementos para OP_CHECKSIG");
        }

        byte[] pubKey = stack.pop();
        byte[] signature = stack.pop();

        boolean valid = CryptoUtils.checkSig(signature, pubKey);
        stack.push(booleanToBytes(valid));
    }

    private boolean isZero(byte[] data) {
        for (byte b : data) {
            if (b != 0) return false;
        }
        return true;
    }

    private byte[] booleanToBytes(boolean value) {
        return value ? new byte[]{1} : new byte[]{0};
    }
}