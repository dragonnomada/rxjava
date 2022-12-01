package com.nomadacode;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Lab1TestDB {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {

        Connection connection;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "lab";
        String driver = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String password = "password";

        Class.forName(driver).getDeclaredConstructor().newInstance();

        connection = DriverManager.getConnection(url + dbName, user, password);

        System.out.println("Conectado correctamente a la base de datos");

        PreparedStatement statement = connection.prepareStatement("SELECT NOW()");

        boolean result = statement.execute();

        System.out.println(result);

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            Timestamp now = resultSet.getTimestamp(1);
            System.out.println(now);
        }

        statement.close();

        connection.close();

        System.out.println("Conexión cerrada exitosamente");

    }

}
