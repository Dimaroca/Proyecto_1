package script;

/**
 * Interfaz genérica que define el comportamiento
 * básico de una estructura tipo pila.
 *
 * @param <T> tipo de elementos almacenados
 */
public interface ADTStack<T> {

    /**
     * Inserta un elemento en la pila.
     *
     * @param item elemento a insertar
     */
    void push(T item);

    /**
     * Elimina y retorna el elemento superior de la pila.
     *
     * @return elemento en la cima de la pila
     */
    T pop();

    /**
     * Retorna el elemento superior sin eliminarlo.
     *
     * @return elemento en la cima de la pila
     */
    T peek();

    boolean isEmpty();
}