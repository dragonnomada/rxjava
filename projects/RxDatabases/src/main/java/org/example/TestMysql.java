package org.example;

import org.junit.Test;

public class TestMysql {

    @Test
    public void testMysql() throws Exception {

        Mysql mysql = new Mysql("com.mysql.cj.jdbc.Driver",
                "localhost", 3306, "rxlab",
                "root", "password");

        mysql.close();

    }

}
