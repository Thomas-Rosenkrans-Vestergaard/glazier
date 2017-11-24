package tvestergaard.glazier.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * An abstract base for any DAOs using the {@link MysqlDataSource}.
 *
 * @author Thomas
 */
public abstract class AbstractMysqlDAO {

    /**
     * The {@link MysqlDataSource} from which to access the data manipulated by
     * descendants of the {@link AbstractMysqlDAO}.
     */
    private final MysqlDataSource source;

    /**
     * The {@link Connection} used by descendants of the
     * {@link AbstractMysqlDAO}.
     */
    private Connection connection;

    /**
     * Creates a new {@link AbstractMysqlDAO}.
     *
     * @param source The {@link MysqlDataSource} from which to access the data
     * manipulated by descendants of the {@link AbstractMysqlDAO}.
     */
    public AbstractMysqlDAO(MysqlDataSource source) {
        this.source = source;
    }

    /**
     * Returns the {@link Connection} used by descendants of the
     * {@link AbstractMysqlDAO}. Only one connection is returned, even after
     * multiple calls to the {@link AbstractMysqlDAO#getConnection()} method.
     *
     * @return The {@link Connection} used by descendants of the
     * {@link AbstractMysqlDAO}.
     * @throws SQLException
     */
    protected final Connection getConnection() throws SQLException {
        if (connection == null) {
            this.connection = source.getConnection();
            this.connection.setAutoCommit(false);
        }

        return this.connection;
    }
}
