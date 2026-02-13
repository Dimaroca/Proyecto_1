package script;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implementación concreta del ADT {@link StackADT} utilizando
 * una estructura {@link ArrayDeque}.
 *
 * Esta implementación respeta el comportamiento LIFO, 
 * donde el último elemento insertado
 * es el primero en ser removido.</p>
 *
 * @param <T> tipo de elementos almacenados en la pila
 */
public class ArrayStack<T> implements StackADT<T> {

    /** Estructura utilizada para almacenar los elementos */
    private Deque<T> data;

    /**
     * Construye una pila vacía.
     */
    public ArrayStack() {
        this.data = new ArrayDeque<>();
    }

    /**
     * Inserta un elemento en el tope de la pila.
     *
     * @param value elemento a insertar
     * @post el elemento queda en la cima de la pila
     * @complexity O(1)
     */
    @Override
    public void push(T value) {
        data.push(value);
    }

    /**
     * Elimina y retorna el elemento superior de la pila.
     *
     * @return elemento que estaba en la cima
     * @throws RuntimeException si la pila está vacía
     * @pre la pila no debe estar vacía
     * @post el tamaño de la pila disminuye en uno
     * @complexity O(1)
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede hacer pop de una pila vacía");
        }
        return data.pop();
    }

    /**
     * Retorna el elemento superior de la pila sin eliminarlo.
     *
     * @return elemento en la cima
     * @throws RuntimeException si la pila está vacía
     * @pre la pila no debe estar vacía
     * @post la pila permanece sin cambios
     * @complexity O(1)
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("No se puede hacer peek de una pila vacía");
        }
        return data.peek();
    }

    /**
     * Indica si la pila está vacía.
     *
     * @return true si no contiene elementos, false en caso contrario
     * @complexity O(1)
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Retorna la cantidad de elementos almacenados en la pila.
     *
     * @return número de elementos en la pila
     * @complexity O(1)
     */
    @Override
    public int size() {
        return data.size();
    }

    /**
     * Retorna una representación en cadena de la pila.
     *
     * @return representación textual de los elementos
     */
    @Override
    public String toString() {
        return data.toString();
    }
}