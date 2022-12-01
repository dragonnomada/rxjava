package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.function.Consumer;

public class Mysql {

    private Connection connection;

    private String url;

    public Mysql(String driver, String host, int port, String db, String user, String password) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName(driver).getDeclaredConstructor().newInstance();

        url = String.format("jdbc:mysql://%s:%d/%s", host, port, db);

        connection = DriverManager.getConnection(url, user, password);

        System.out.printf("Conectado a la base de datos: %s %n", this);

    }

    public Mysql(String db, String user, String password) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        url = String.format("jdbc:mysql://%s:%d/%s", "localhost", 3306, db);

        connection = DriverManager.getConnection(url, user, password);

        System.out.printf("Conectado a la base de datos: %s %n", this);

    }

    public void close() throws SQLException {
        connection.close();
        System.out.printf("Desconectado de la base de datos: %s %n", this);
    }

    public Observable<Connection> getConnection() {
        return Observable.just(connection);
    }

    public Observable<ResultSet> exectuteQuery(String sql, Consumer<PreparedStatement> onStatementConfig) {
        return this.getConnection()
                // Observable<Connection> -> Observable<PreparedStatement>
                .map(connection -> connection.prepareStatement(sql))
                .doAfterNext(statement -> {
                    System.out.printf("Cerrando preparedStament [%s] %n", sql);
                    statement.close();
                })
                // Observable<PreparedStatement>
                .doOnNext(statement -> {
                    // Mandamos a ejecutar el lambda de Consumer<PreparedStatement>
                    onStatementConfig.accept(statement);
                })
                // Observable<PreparedStatement>
                .doOnNext(statement -> {
                    // Ejecutamos el statement
                    statement.execute();
                })
                // Observable<PreparedStatement> -> Observable<ResultSet>
                .map(statement -> statement.getResultSet())
                .doAfterNext(resultSet -> {
                    System.out.printf("Cerrando resultSet [%s] %n", sql);
                    resultSet.close();
                })
                // Observable<ResultSet>
                .flatMap(resultSet -> Observable.create(emitter -> {
                    while (resultSet.next()) {
                        emitter.onNext(resultSet);
                    }
                    emitter.onComplete();
                }))
                // Observable<Object> -> Observable<ResultSet>
                .cast(ResultSet.class);
    }

    @Override
    public String toString() {
        return String.format("[%s]", url);
    }
}
