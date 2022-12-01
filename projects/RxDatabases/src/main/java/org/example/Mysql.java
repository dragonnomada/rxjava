package org.example;


import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {

    private Connection connection;

    private String url;

    public Mysql(String driver, String host, int port, String db, String user, String password) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName(driver).getDeclaredConstructor().newInstance();

        url = String.format("jdbc:mysql://%s:%d/%s", host, port, db);

        connection = DriverManager.getConnection(url, user, password);

        System.out.printf("Conectado a la base de datos: %s %n", this);

    }

    public void close() throws SQLException {
        connection.close();
        System.out.printf("Desconectado de la base de datos: %s %n", this);
    }

    @Override
    public String toString() {
        return String.format("[%s]", url);
    }
}
