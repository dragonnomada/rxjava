package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

class MysqlConnection {

    Connection connection;

    String url;

    public MysqlConnection(String uri, String db, String user, String password) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        url = uri + db;
        connection = DriverManager.getConnection(url, user, password);
    }

    public MysqlConnection(String db, String user, String password) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        url = "jdbc:mysql://localhost:3306/" + db;
        connection = DriverManager.getConnection(url, user, password);
    }

    public void close() throws SQLException {
        System.out.printf("Cerrando conexión [%s] %n", url);
        connection.close();
    }

    public Observable<Connection> getConnection() {
        return Observable.just(connection);
    }

    public Observable<ResultSet> executeQuery(String sql, Consumer<PreparedStatement> onStatement) {
        //AtomicReference<PreparedStatement> statementAtomicReference = new AtomicReference<>();
        //AtomicReference<ResultSet> resultSetAtomicReference = new AtomicReference<>();
        return getConnection()
                /*.doFinally(() -> {
                    System.out.printf("Cerrando resultset [%s] %n", sql);
                    resultSetAtomicReference.get().close();
                    System.out.printf("Cerrando statement [%s] %n", sql);
                    statementAtomicReference.get().close();
                })*/
                .map(connection -> {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    //statementAtomicReference.set(statement);
                    return statement;
                })
                .doAfterNext(statement -> {
                    System.out.printf("Cerrando statement [%s] %n", sql);
                    statement.close();
                })
                .map(statement -> {
                    boolean result = statement.execute();
                    if (result) {
                        ResultSet resultSet = statement.getResultSet();
                        //resultSetAtomicReference.set(resultSet);
                        return resultSet;
                    } else {
                        String error = String.format("Error executing query [%s]", sql);
                        throw new SQLException(error);
                    }
                })
                .doAfterNext(resultSet -> {
                    System.out.printf("Cerrando resultset [%s] %n", sql);
                    resultSet.close();
                })
                .flatMap(resultSet -> {
                    return Observable.create(emitter -> {
                        while (resultSet.next()) {
                            //System.out.println(resultSet.isLast());
                            //System.out.println(resultSet.isClosed());
                            emitter.onNext(resultSet);
                        }
                        System.out.printf("Terminando resultset [%s] %n", sql);
                    });
                })
                .cast(ResultSet.class);
    }

    public Observable<ResultSet> executeQuery(String sql) {
        return executeQuery(sql, statement -> {
        });
    }

}
class LabProduct {

    private int id;
    private String name;
    private double price;
    private int existences;
    private boolean active;
    private Timestamp createAt;
    private Timestamp updateAt;

    public LabProduct(int id, String name, double price, int existences, boolean active, Timestamp createAt, Timestamp updateAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.existences = existences;
        this.active = active;
        this.createAt = createAt;
        this.updateAt = updateAt;
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

    public int getExistences() {
        return existences;
    }

    public boolean isActive() {
        return active;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-20s $%04.2f (%d) [ACTIVO=%B] [C: %s U: %s]",
                id, name, price, existences, active, createAt, updateAt);
    }
}

public class Lab3TestDB {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {

        MysqlConnection connection = new MysqlConnection("lab", "root", "password");

        connection.executeQuery("SELECT * FROM productos")
                .map(resultSet -> {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    double precio = resultSet.getDouble("precio");
                    int existencias = resultSet.getInt("existencias");
                    boolean activo = resultSet.getBoolean("activo");
                    Timestamp creado = resultSet.getTimestamp("creado");
                    Timestamp actualizado = resultSet.getTimestamp("actualizado");
                    return new LabProduct(id, nombre, precio, existencias, activo, creado, actualizado);
                })
                //.observeOn(Schedulers.io())
                .subscribe(labProduct -> {
                    System.out.println(labProduct);
                });

        connection.close();

    }

}
