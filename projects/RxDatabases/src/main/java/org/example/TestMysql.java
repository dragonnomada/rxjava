package org.example;

import io.reactivex.rxjava3.core.Observable;
import org.example.entinty.Prueba;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TestMysql {

    @Test
    public void testMysql() throws Exception {

        Mysql mysql = new Mysql("com.mysql.cj.jdbc.Driver",
                "localhost", 3306, "rxlab",
                "root", "password");

        mysql.close();

    }

    @Test
    public void testMysqlQuery() throws Exception {

        // Se abre la conexión
        Mysql mysql = new Mysql("com.mysql.cj.jdbc.Driver",
                "localhost", 3306, "rxlab",
                "root", "password");

        String sql = "select * from prueba where activo = ?";
        Consumer<PreparedStatement> onStatementConfig = statement -> {
            try {
                statement.setBoolean(1, true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        // Observable<Connection>
        Observable<Map<String, Object>> sourceRecords = mysql.getConnection()
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
                .cast(ResultSet.class)
                // Observable<ResultSet> -> Observable<Map<String, Object>>
                .map(resultSet -> {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    Timestamp creado = resultSet.getTimestamp("creado");
                    Timestamp actualizado = resultSet.getTimestamp("actualizado");
                    boolean activo = resultSet.getBoolean("activo");
                    Map<String, Object> record = new HashMap<>();
                    record.put("id", id);
                    record.put("name", nombre);
                    record.put("createAt", creado);
                    record.put("updateAt", actualizado);
                    record.put("active", activo);
                    return record;
                    //Prueba prueba = new Prueba(id, nombre, creado, actualizado, activo);
                    //return String.format("[%d] %s (C: %s U: %s A: %B)", id, nombre, creado, actualizado, activo);
                });

        // Estratégia asíncrona: Sigue la cadena de operaciones con un observador más "el último observador"
        sourceRecords.subscribe(record -> {
            System.out.printf("[%d] %s creado el %s %n", record.get("id"), record.get("name"), record.get("createAt"));
        });

        // Estrategia síncrona: Esto se funsiona con el hilo principal "independiente de la cadena de observadores"
        // Esto es similar a utilizar `.cache()`
        /*Iterable<Map<String, Object>> records = sourceRecords.blockingIterable();

        for (Map<String, Object> record : records) {
            System.out.printf("[%d] %s creado el %s %n", record.get("id"), record.get("name"), record.get("createAt"));
        }*/

        mysql.close();

    }

    @Test
    public void testExecuteQuery() throws Exception {

        Mysql mysql = new Mysql("rxlab", "root", "password");

        List<Prueba> pruebas = mysql.exectuteQuery("select * from prueba where activo = ?", statement -> {
                    try {
                        statement.setBoolean(1, true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).map(resultSet -> {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    Timestamp creado = resultSet.getTimestamp("creado");
                    Timestamp actualizado = resultSet.getTimestamp("actualizado");
                    boolean activo = resultSet.getBoolean("activo");
                    Prueba prueba = new Prueba(id, nombre, creado, actualizado, activo);
                    return prueba;
                })
                .toList()
                .blockingGet();

        Observable.fromIterable(pruebas)
                        .subscribe(prueba -> System.out.println(prueba));

        mysql.close();

    }

}
