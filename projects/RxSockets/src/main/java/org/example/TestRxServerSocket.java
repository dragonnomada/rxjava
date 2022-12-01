package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestRxServerSocket {

    public static void main(String[] args) throws IOException {

        RxServerSocket serverSocket = new RxServerSocket(4000);

        Observable<Socket> sourceClientSocket = serverSocket.getServerSocket()
                .doOnNext(serverSocket1 -> {
                    System.out.println("Esperando clientes...");
                })
                .switchMap(serverSocket1 -> Observable.create(emitter -> {
                    while (!emitter.isDisposed()) {
                        Socket socket = serverSocket1.accept();
                        emitter.onNext(socket);
                    }
                    emitter.onComplete();
                }).cast(Socket.class))
                .doOnNext(socket -> {
                    System.out.println("Cliente aceptado");

                    System.out.println("Obteniendo petición del cliente...");

                    ClientRequestType clientRequestType = Observable.just(socket)
                            .compose(RxServerSocket.getClientRequestTypeComposeOperator())
                            .blockingFirst();

                    switch (clientRequestType) {
                        case GREETING -> {
                            boolean result = Observable.just(socket)
                                    .compose(RxServerSocket.sendGreeting("Hola cliente :D"))
                                    .blockingFirst();

                            if (result) {
                                System.out.println("Se le ha enviado un saludo al cliente");
                            } else {
                                System.out.println("Error al enviarle el saludo al cliente");
                            }
                        }
                        case GET_PRODUCTOS -> {
                            boolean result = Observable.just(socket)
                                    .compose(RxServerSocket.sendProductos())
                                    .blockingFirst();

                            if (result) {
                                System.out.println("Se le han enviado los productos al cliente");
                            } else {
                                System.out.println("Error al enviarle los productos al cliente");
                            }
                        }
                    }

                });

        sourceClientSocket.subscribe();

        System.out.println("Cerrando el servidor");
        serverSocket.close();

    }

}
