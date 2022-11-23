package org.example;

// Toda interfaz funcional puede ser consumida
// como una expresión lambda.
// Es decir, en lugar de hacer:
// new IEncenderCarro() {
//    @Override
//    public void encender(String llave) {
//        ... code here
//    }
// }
// Podemos hacer:
// (String llave) -> { ...code here }
// Opcionalmente podemos anotar para los programadores:
@FunctionalInterface
public interface IEncenderCarro {

    void encender(String llave);

}
