package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

enum CustomProductRequestType {
    ALL,
    FIRST,
    BY_PRICE
}

class CustomProduct implements Serializable {

    int id;
    String name;
    double price;

    public CustomProduct(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "CustomProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

public class Lab4ServerSocket {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket serverSocket = new ServerSocket(4000);

        System.out.println("Servidor iniciado");

        Socket socket = serverSocket.accept();

        System.out.println("Cliente recibido");

        InputStream inputStream = socket.getInputStream();

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        CustomProductRequestType productRequestType = (CustomProductRequestType)objectInputStream.readObject();

        List<CustomProduct> products = new ArrayList<>();

        products.add(new CustomProduct(1, "A", 12.34));
        products.add(new CustomProduct(2, "B", 56.78));
        products.add(new CustomProduct(3, "C", 90.12));
        products.add(new CustomProduct(4, "D", 34.56));

        switch (productRequestType) {
            case ALL -> {
                for (CustomProduct customProduct : products) {
                    OutputStream outputStream = socket.getOutputStream();

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                    objectOutputStream.writeObject(CustomProductResponseType.NEXT);

                    OutputStream outputStream1 = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(outputStream);
                    objectOutputStream1.writeObject(customProduct);
                }

                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(CustomProductResponseType.DONE);
            }
            case FIRST -> {

            }
            case BY_PRICE -> {

            }
            default -> {

            }
        }

    }

}
