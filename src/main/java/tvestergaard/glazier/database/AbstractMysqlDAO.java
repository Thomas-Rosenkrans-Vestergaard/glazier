package tvestergaard.glazier.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractMysqlDAO {

    private final MysqlDataSource source;
    private Connection connection;

    public AbstractMysqlDAO(MysqlDataSource source) {
        this.source = source;
    }

    protected final Connection getConnection() throws SQLException {
        if (connection == null) {
            this.connection = source.getConnection();
            this.connection.setAutoCommit(false);
        }

        return this.connection;
    }
}
