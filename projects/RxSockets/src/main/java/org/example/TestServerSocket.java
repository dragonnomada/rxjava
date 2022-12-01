package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class TestServerSocket {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // [Ciclo de vida largo]
        ServerSocket serverSocket = new ServerSocket(4000);

        System.out.println("Servidor iniciado");

        boolean detenido = false;

        Thread thread = new Thread(() -> {
            while (!detenido) {
                // [Ciclo de corto]
                // 1. Aceptar un cliente (Socket)
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Cliente aceptado");

                // 2. Transmitir (input-output) datos con el clientes
                //    Peer-To-Peer (díalogo)

                // Diálogo:
                // 1. Servidor recibe un objeto de tipo ClientRequestType desde el cliente
                InputStream clientInputStream = null;
                try {
                    clientInputStream = clientSocket.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ObjectInputStream clientObjectInputStream = null;
                try {
                    clientObjectInputStream = new ObjectInputStream(clientInputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ClientRequestType clientRequestType = null;
                try {
                    clientRequestType = (ClientRequestType) clientObjectInputStream.readObject();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Peteción de cliente recibida");

                System.out.println(clientRequestType);

                switch (clientRequestType) {
                    case GREETING -> {
                        System.out.println("El cliente solicita un saludo");
                        OutputStream clientOutputStream = null;
                        try {
                            clientOutputStream = clientSocket.getOutputStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ObjectOutputStream clientObjectOutputStream = null;
                        try {
                            clientObjectOutputStream = new ObjectOutputStream(clientOutputStream);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            clientObjectOutputStream.writeObject(ServerResponseType.RESPONSE_GREETING);
                            System.out.println("Se le ha notificado al cliente que se responderá su saludo");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Greeting greeting = new Greeting("Hola cliente");

                        OutputStream clientOutputStream1 = null;
                        try {
                            clientOutputStream1 = clientSocket.getOutputStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ObjectOutputStream clientObjectOutputStream1 = null;
                        try {
                            clientObjectOutputStream1 = new ObjectOutputStream(clientOutputStream1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            clientObjectOutputStream1.writeObject(greeting);
                            System.out.println("Saludo enviado al cliente :D");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        thread.start();

        thread.join();

        serverSocket.close();

    }

}
