package script;

/**
 * Interfaz genérica que define el comportamiento
 * de una estructura de datos tipo pila.
 *
 * <p>Una pila es una estructura LIFO (Last In, First Out),
 * donde el último elemento insertado es el primero en salir.</p>
 *
 * @param <T> tipo de elementos almacenados en la pila
 */
public interface StackADT<T> {

    /**
     * Inserta un elemento en el tope de la pila.
     *
     * @param value elemento a insertar
     * @post el elemento queda en la cima de la pila
     * @complexity O(1)
     */
    void push(T value);

    /**
     * Elimina y retorna el elemento superior de la pila.
     *
     * @return elemento que estaba en la cima
     * @pre la pila no debe estar vacía
     * @post el tamaño de la pila disminuye en uno
     * @complexity O(1)
     */
    T pop();

    /**
     * Retorna el elemento superior de la pila sin eliminarlo.
     *
     * @return elemento en la cima de la pila
     * @pre la pila no debe estar vacía
     * @post la pila permanece sin cambios
     * @complexity O(1)
     */
    T peek();

    /**
     * Indica si la pila está vacía.
     *
     * @return true si no contiene elementos, false en caso contrario
     * @complexity O(1)
     */
    boolean isEmpty();

    /**
     * Retorna el número de elementos almacenados en la pila.
     *
     * @return cantidad de elementos en la pila
     * @complexity O(1)
     */
    int size();
}
