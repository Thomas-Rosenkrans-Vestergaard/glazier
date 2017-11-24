package tvestergaard.glazier.database;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DefaultMysqlSource extends MysqlDataSource {

    /**
     * Creates a new {@link DefaultMysqlSource}.
     */
    public DefaultMysqlSource() {
        setServerName("localhost");
        setUser("thomas");
        setPassword("1733");
        setDatabaseName("glazier");
    }
}
