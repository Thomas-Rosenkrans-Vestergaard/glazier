/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.database;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 *
 * @author Thomas
 */
public class DefaultMysqlSource extends MysqlDataSource {

    public DefaultMysqlSource() {
        setServerName("localhost");
        setUser("root");
        setPassword("2231302509");
        setDatabaseName("glazier");
    }

    private static DefaultMysqlSource source;

    public static MysqlDataSource getSource() {
        if (source == null) {
            source = new DefaultMysqlSource();
        }

        return source;
    }
}
