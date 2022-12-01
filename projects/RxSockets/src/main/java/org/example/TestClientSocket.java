package org.example;

import java.io.*;
import java.net.Socket;

public class TestClientSocket {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket clienteSocket = new Socket("localhost", 4000);

        System.out.println("Cliente conectado");

        // 1. Comenzar el diálogo:
        //    Server-First
        //    >>> Client-First

        // Nota: Para enviarle algo al servidor mediante el socket
        // lo más común es preparar un `objeto` que envuelva los datos
        // El único requerimiento es que el `objeto` sea serializable
        // La mayoría de las clases comúnes y enumeraciones ya son serializables
        // Y para las restantes podemos implementar la interfaz Serializable

        // 1.1 Adquirir el OutputStream hacía el servidor
        // (del clientSocket al servidor)
        // para poder tener una tubería de transmisión de datos
        OutputStream serverOutputStream = clienteSocket.getOutputStream();

        // Creamos un wrapper para potenciar la transmisión y darle capacidad de
        // enviar objetos completos
        ObjectOutputStream serverObjectOutputStream = new ObjectOutputStream(serverOutputStream);

        // Diálogo:
        // 1. Cliente envía objeto de tipo ClientRequestType al servidor

        serverObjectOutputStream.writeObject(ClientRequestType.GET_PRODUCTOS);

        System.out.println("Petición de cliente enviada");

        // Diálogo:
        // 2. Cliente espera un ServerResponseType de servidor

        while (true) {
            InputStream clientInputStream = clienteSocket.getInputStream();
            ObjectInputStream clientObjectInputStream = new ObjectInputStream(clientInputStream);
            ServerResponseType serverResponseType = (ServerResponseType) clientObjectInputStream.readObject();

            System.out.println("El servidor ha respondido");
            System.out.println(serverResponseType);

            switch (serverResponseType) {
                case RESPONSE_GREETING -> {
                    // Diálogo
                    // 3. Cliente espera un saludo de tipo Greeting del servidor

                    InputStream clientInputStream1 = clienteSocket.getInputStream();
                    ObjectInputStream clientObjectInputStream1 = new ObjectInputStream(clientInputStream);
                    Greeting serverGreeting = (Greeting) clientObjectInputStream.readObject();

                    System.out.println("El servidor mandó el saludo");
                    System.out.println(serverGreeting);
                }
                case RESPONSE_NEXT_PRODUCTO -> {
                    InputStream clientInputStream1 = clienteSocket.getInputStream();
                    ObjectInputStream clientObjectInputStream1 = new ObjectInputStream(clientInputStream1);
                    Producto producto = (Producto) clientObjectInputStream1.readObject();

                    System.out.println("El servidor mandó un producto más");
                    System.out.println(producto);
                }
            }
        }

        //clienteSocket.close();

    }

}
