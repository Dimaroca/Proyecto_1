Intérprete de Bitcoin fase 1

Integrantes: 
Miguel Sajquín (252149)
Rodrigo (25)
Diego (25)

Este proyecto consiste en la implementación de un intérprete para un subconjunto de Bitcoin Script, desarrollado como parte del curso de Algoritmos y Estructuras de Datos.

El sistema simula el proceso de validación de scripts tipo P2PKH (Pay To Public Key Hash) utilizando una máquina basada en pila.

La implementación aplica conceptos fundamentales como:

- ADT (Abstract Data Type)
- Genéricos
- Encapsulación
- Separación de responsabilidades
- Complejidad temporal
- Uso del Java Collections Framework (JCF)

Para la prueba P2PKH, es importante entender que el programa actualmente espera que se ingrese un archivo .txt que tenga en la primera linea la firma separada por comas y en la segunda linea
la public key separa por comas igualmente. Algo así:

1,2,4,4 (firma)
1,2,4,4 (pubkey)

de no ser así, se puede utilizar este main para probar el programa:

package script;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        byte[] firma = new byte[]{#numeros a probar};
        byte[] pubKey = new byte[]{#numeros a probar};
        
        List<Instruction> script = P2PKHScontrolador.build(firma, pubKey);
        ScriptEngine interpreter = new Interpreter(true);
        
        boolean result = interpreter.execute(script);
        System.out.println("Resultado: " + result);

    }
}
