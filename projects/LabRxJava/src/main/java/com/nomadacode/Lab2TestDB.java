package com.nomadacode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.sql.*;
import java.util.function.Consumer;

class Mysql {

    public static Observable<Connection> createConnection(String driver, String uri, String db, String user, String password) {
        return Observable.using(() -> {
            Class.forName(driver).getDeclaredConstructor().newInstance();
            String url = uri + db;
            return DriverManager.getConnection(url, user, password);
        }, connection -> {
            return Observable.just(connection);
        }, connection -> {
            System.out.println("Cerrando la conexión");
            //Sleep.sleep(5000);
            connection.close();
        });

        /*return Observable.create(emitter -> {
                    try {
                        Class.forName(driver).getDeclaredConstructor().newInstance();
                    } catch (Throwable e) {
                        emitter.onError(e);
                        return;
                    }

                    String url = uri + db;

                    try {
                        Connection connection = DriverManager.getConnection(url, user, password);
                        emitter.onNext(connection);
                        //emitter.onComplete();
                    } catch (SQLException e) {
                        emitter.onError(e);
                        return;
                    }
                }).cast(Connection.class)
                .doAfterNext(connection -> {
                    System.out.println("Cerrando la conexión");
                    //Sleep.sleep(5000);
                    connection.close();
                });*/
    }

    public static Observable<Connection> createConnection(String uri, String db, String user, String password) {
        return Mysql.createConnection("com.mysql.cj.jdbc.Driver", uri, db, user, password);
    }

    public static Observable<Connection> createConnection(String db, String user, String password) {
        return Mysql.createConnection("jdbc:mysql://localhost:3306/", db, user, password);
    }

    public static ObservableTransformer<Connection, PreparedStatement> createQuery(String sql, Consumer<PreparedStatement> onPreparedStatement) {
        return upstreamConnection -> upstreamConnection
                .map(connection -> connection.prepareStatement(sql))
                .map(statement -> {
                    onPreparedStatement.accept(statement);
                    return statement;
                })
                .doAfterNext(statement -> {
                    System.out.println("Cerrando el statement");
                    //Sleep.sleep(4000);
                    statement.close();
                });
    }

    public static ObservableTransformer<Connection, PreparedStatement> createQuery(String sql) {
        return Mysql.createQuery(sql, statement -> {
        });
    }

    public static ObservableTransformer<PreparedStatement, ResultSet> executeQuery() {
        return upstreamStatement -> upstreamStatement
                .map(statement -> {
                    boolean result = statement.execute();
                    if (result) {
                        return statement.getResultSet();
                    }
                    throw new SQLException("Empty execute");
                })
                .doAfterNext(resultSet -> {
                    System.out.println("Cerrando el resultSet");
                    //Sleep.sleep(3000);
                    resultSet.close();
                })
                .flatMap(resultSet -> {
                    return Observable.create(emitter -> {
                        while (resultSet.next()) {
                            emitter.onNext(resultSet);
                        }
                    });
                })
                .cast(ResultSet.class);
    }

}

public class Lab2TestDB {

    public static void main(String[] args) {

        Observable<ResultSet> querySource = Mysql.createConnection("lab", "root", "password")
                .compose(Mysql.createQuery("SELECT *, now() as now FROM productos"))
                .compose(Mysql.executeQuery());

        querySource.subscribe(resultSet -> {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            double precio = resultSet.getDouble("precio");
            int existencias = resultSet.getInt("existencias");
            boolean activo = resultSet.getBoolean("activo");
            Timestamp creado = resultSet.getTimestamp("creado");
            Timestamp actualizado = resultSet.getTimestamp("actualizado");
            Timestamp now = resultSet.getTimestamp("now");

            System.out.printf("[%d] %-20s $%04.2f (%d) [ACTIVO=%B] [C: %s U: %s N: %s] %n",
                    id, nombre, precio, existencias, activo, creado, actualizado, now);
        });

        querySource.subscribe(resultSet -> {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            double precio = resultSet.getDouble("precio");
            int existencias = resultSet.getInt("existencias");
            boolean activo = resultSet.getBoolean("activo");
            Timestamp creado = resultSet.getTimestamp("creado");
            Timestamp actualizado = resultSet.getTimestamp("actualizado");
            Timestamp now = resultSet.getTimestamp("now");

            System.out.printf("[%d] %-20s $%04.2f (%d) [ACTIVO=%B] [C: %s U: %s N: %s] %n",
                    id, nombre, precio, existencias, activo, creado, actualizado, now);
        });

    }

}
