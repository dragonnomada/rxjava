package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.observers.DisposableObserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

public class RxServerSocket {

    ServerSocket serverSocket;

    public RxServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public Observable<ServerSocket> getServerSocket() {
        return Observable.just(serverSocket);
    }

    public static ObservableTransformer<Socket, Boolean> sendGreeting(String message) {
        return upstream -> upstream
                .map(socket -> socket.getOutputStream())
                .map(outputStream -> new ObjectOutputStream(outputStream))
                .doOnNext(objectOutputStream -> {
                    objectOutputStream.writeObject(ServerResponseType.RESPONSE_GREETING);
                })
                .switchMap(objectOutputStream -> upstream)
                .map(socket -> socket.getOutputStream())
                .map(outputStream -> new ObjectOutputStream(outputStream))
                .doOnNext(objectOutputStream -> {
                    objectOutputStream.writeObject(new Greeting(message));
                })
                .switchMap(objectOutputStream -> Observable.just(true))
                .onErrorResumeWith(Observable.just(false));
    }

    public static ObservableTransformer<Socket, Boolean> sendProductos() {
        return upstream -> upstream
                //.map(socket -> socket.getOutputStream())
                //.map(outputStream -> new ObjectOutputStream(outputStream))
                //.doOnNext(objectOutputStream -> {
                //    objectOutputStream.writeObject(ServerResponseType.RESPONSE_PRODUCTOS);
                //})
                .switchMap(objectOutputStream -> Observable
                        .just(new Mysql("lab", "root", "password"))
                        .cast(Mysql.class))
                .doAfterNext(mysql -> {
                    mysql.close();
                })
                .switchMap(mysql -> mysql.exectuteQuery("select * from productos", statement -> {}))
                .map(resultSet -> {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    double precio = resultSet.getDouble("precio");
                    int existencias = resultSet.getInt("existencias");
                    Timestamp creado = resultSet.getTimestamp("creado");
                    Timestamp actualizado = resultSet.getTimestamp("creado");
                    boolean activo = resultSet.getBoolean("activo");
                    Producto producto = new Producto(id, nombre, precio, existencias, creado, actualizado, activo);
                    System.out.println(producto);
                    return producto;
                })
                // TODO: Notificar primer y último producto
                .switchMap(producto -> upstream
                        .map(socket -> socket.getOutputStream())
                        .map(outputStream -> new ObjectOutputStream(outputStream))
                        .doOnNext(objectOutputStream -> {
                            objectOutputStream.writeObject(ServerResponseType.RESPONSE_NEXT_PRODUCTO);
                        })
                        .switchMap(objectOutputStream -> upstream)
                        .map(socket -> socket.getOutputStream())
                        .map(outputStream -> new ObjectOutputStream(outputStream))
                        .doOnNext(objectOutputStream -> {
                            objectOutputStream.writeObject(producto);
                        })
                )
                .switchMap(objectOutputStream -> Observable.just(true));

    }

    public static ObservableTransformer<Socket, ClientRequestType> getClientRequestTypeComposeOperator() {
        return upstream -> upstream
                .map(socket -> socket.getInputStream())
                .map(inputStream -> new ObjectInputStream(inputStream))
                .map(objectInputStream -> objectInputStream.readObject())
                .cast(ClientRequestType.class);
    }

    public static ObservableOperator<ClientRequestType, Socket> getClientRequestTypeLiftOperator() {
        return observer -> new DisposableObserver<Socket>() {
            @Override
            public void onNext(@NonNull Socket socket) {
                InputStream inputStream = null;
                try {
                    inputStream = socket.getInputStream();
                } catch (IOException e) {
                    observer.onError(e);
                }
                ObjectInputStream objectInputStream = null;
                try {
                    objectInputStream = new ObjectInputStream(inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ClientRequestType clientRequestType = null;
                try {
                    clientRequestType = (ClientRequestType) objectInputStream.readObject();
                    observer.onNext(clientRequestType);
                    observer.onComplete();
                } catch (IOException e) {
                    observer.onError(e);
                } catch (ClassNotFoundException e) {
                    observer.onError(e);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

}
