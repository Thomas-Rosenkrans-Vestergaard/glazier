package tvestergaard.glazier.database;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DefaultMysqlSource extends MysqlDataSource {

    /**
     * Creates a new {@link DefaultMysqlSource}.
     */
    public DefaultMysqlSource() {
        setServerName("localhost");
        setUser("root");
        setPassword("2231302509");
        setDatabaseName("glazier");
    }
}
