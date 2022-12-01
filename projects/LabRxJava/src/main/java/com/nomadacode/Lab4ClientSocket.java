package com.nomadacode;

import java.io.*;
import java.net.Socket;

enum CustomProductResponseType {
    NEXT,
    DONE
}

public class Lab4ClientSocket {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 4000);

        System.out.println("Cliente iniciado");

        OutputStream outputStream = socket.getOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(CustomProductRequestType.ALL);

        nextResponse(socket);

    }

    private static void nextResponse(Socket socket) throws IOException, ClassNotFoundException {
        InputStream inputStream = socket.getInputStream();

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        CustomProductResponseType customProductResponseType = (CustomProductResponseType) objectInputStream.readObject();

        switch (customProductResponseType) {
            case NEXT -> {
                InputStream inputStream1 = socket.getInputStream();
                ObjectInputStream objectInputStream1 = new ObjectInputStream(inputStream1);
                CustomProduct product = (CustomProduct) objectInputStream1.readObject();
                System.out.println(product);
                nextResponse(socket);
            }
        }
    }

}
