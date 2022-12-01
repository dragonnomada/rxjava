package org.example;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class TestConnection {

    @Test
    public void testConnection() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rxlab", "root", "password");

        Assert.assertFalse(connection.isClosed());

        connection.close();

        Assert.assertTrue(connection.isClosed());

    }

    @Test
    public void testSelectPrueba() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rxlab", "root", "password");

        Assert.assertFalse(connection.isClosed());

        String sql = "select * from prueba";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // TODO: Configurar los parámetros del preparedStatement

        boolean result = preparedStatement.execute();

        Assert.assertTrue(result);

        ResultSet resultSet = preparedStatement.getResultSet();

        Assert.assertFalse(resultSet.isClosed());

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            Timestamp creado = resultSet.getTimestamp("creado");
            Timestamp actualizado = resultSet.getTimestamp("actualizado");
            boolean activo = resultSet.getBoolean("activo");

            System.out.printf("[%d] %s (C: %s U: %s A: %B) %n", id, nombre, creado, actualizado, activo);
        }

        Assert.assertFalse(resultSet.next());

        resultSet.close();

        preparedStatement.close();

        connection.close();

        Assert.assertTrue(resultSet.isClosed());
        Assert.assertTrue(preparedStatement.isClosed());

        // x

        Assert.assertTrue(connection.isClosed());
    }

    @Test
    public void testSelectPruebaActivos() throws Exception {
        // 1. Abrir la conexión [Ciclo de vida completo]
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rxlab", "root", "password");

        Assert.assertFalse(connection.isClosed());

        // --- INICIA QUERY ---

        // 2. Preparar el Query (PreparedStatement) [Ciclo de vida limitado]

        // >>> Un parámetro de query identificado
        String sql = "select * from prueba where activo = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // TODO: Configurar los parámetros del preparedStatement
        // >>> Identificamos un conjunto de operaciones que se deben aplicar al preparedStatement
        preparedStatement.setBoolean(1, false);

        // 3. Ejecución y adequisición del resultSet

        boolean result = preparedStatement.execute();

        Assert.assertTrue(result);

        ResultSet resultSet = preparedStatement.getResultSet();

        Assert.assertFalse(resultSet.isClosed());

        int count = 0;

        // 4. Recorrer los registros y aplicarlos (Observable para extraer información del resultSet)

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            Timestamp creado = resultSet.getTimestamp("creado");
            Timestamp actualizado = resultSet.getTimestamp("actualizado");
            boolean activo = resultSet.getBoolean("activo");

            System.out.printf("[%d] %s (C: %s U: %s A: %B) %n", id, nombre, creado, actualizado, activo);

            count++;
        }

        Assert.assertEquals(1, count);

        Assert.assertFalse(resultSet.next());

        // 5. Cerramos el resultSet

        resultSet.close();

        // 6. Cerramos el preparedStatement

        preparedStatement.close();

        // --- FIN DEL QUERY ---

        // 7. Cerrar la Conexión [Hasta que termine el programa]

        connection.close();

        Assert.assertTrue(resultSet.isClosed());
        Assert.assertTrue(preparedStatement.isClosed());
        Assert.assertTrue(connection.isClosed());
    }

}
