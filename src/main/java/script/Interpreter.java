package script;

import java.util.Arrays;
import java.util.List;

public class Interpreter {

    public boolean execute(List<Opcode> script) {

        ArrayStack<byte[]> stack = new ArrayStack<>();

        ArrayStack<Boolean> validator = new ArrayStack<>();

        for (Opcode op : script) {

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

                case OP_DUP: {
                    byte[] top = stack.peek();
                    stack.push(top);
                    break;
                }

                case OP_DRUP: {
                    stack.pop();
                    break;
                }

                case OP_EQUAL: {
                    byte[] a = stack.pop();
                    byte[] b = stack.pop();
                    validator.push(Arrays.equals(a, b));
                    break;
                }

                case OP_EQUALVERIFY: {
                    byte[] a = stack.pop();
                    byte[] b = stack.pop();
                    if (!Arrays.equals(a, b)) return false;
                    validator.push(true);
                    break;
                }

                case OP_HASH160:
                case OP_CHECKSIG:
                    throw new UnsupportedOperationException(op + " no implementado todavía");

                default:
                    throw new UnsupportedOperationException("Opcode desconocido: " + op);
            }
        }

        byte[] result = stack.pop();
        return !(result.length == 1 && result[0] == 0);
    }
}
