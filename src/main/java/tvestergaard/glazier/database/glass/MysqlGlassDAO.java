package tvestergaard.glazier.database.glass;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tvestergaard.glazier.database.AbstractMysqlDAO;

public class MysqlGlassDAO extends AbstractMysqlDAO implements GlassDAO {

    /**
     * Creates a new {@link MysqlGlassDAO}.
     *
     * @param source The {@link MysqlDataSource} from which to access the data
     * manipulated by the {@link MysqlGlassDAO}.
     */
    public MysqlGlassDAO(MysqlDataSource source) {
        super(source);
    }

    /**
     * Retrieves and returns the {@link Glass} referenced by the provided
     * {@link GlassReference} from the {@link GlassDAO}.
     *
     * @param glassReference The {@link GlassReference} referencing the
     * {@link Glass} to retrieve return.
     * @return The {@link Glass} referenced by the provided
     * {@link GlassReference}.
     * @throws UnknownGlassReferenceException When the provided
     * {@link GlassReference} doesn't reference an existing {@link Glass}.
     */
    @Override
    public Glass getGlass(GlassReference glassReference) throws UnknownGlassReferenceException {

        try {

            PreparedStatement statement = null;
            ResultSet results = null;

            try {
                String select = "SELECT * FROM glass WHERE id = ?";
                statement = getConnection().prepareStatement(select);
                statement.setInt(1, glassReference.getID());
                results = statement.executeQuery();

                if (!results.first()) {
                    throw new UnknownGlassReferenceException(glassReference);
                }

                return fromResultSet(results);

            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (results != null) {
                    results.close();
                }
            }

        } catch (UnknownGlassReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieves and returns all the {@link Glass}es from the {@link GlassDAO}.
     *
     * @return The compelete list of {@link Glass}es.
     */
    @Override
    public List<Glass> getGlasses() {

        try {

            PreparedStatement statement = null;
            ResultSet glasses = null;

            try {
                String select = "SELECT * FROM  glass";
                statement = getConnection().prepareStatement(select);
                glasses = statement.executeQuery();
                List<Glass> results = new ArrayList<>();

                while (glasses.next()) {
                    results.add(fromResultSet(glasses));
                }

                return results;

            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (glasses != null) {
                    glasses.close();
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Saves any changes to the provided {@link Glass} to the {@link GlassDAO}.
     *
     * @param glass The {@link Glass} to update.
     * @throws UnknownGlassException When the provided {@link Glass} doesn't
     * exist in the {@link GlassDAO}.
     */
    @Override
    public void updateGlass(Glass glass) throws UnknownGlassException {
        String update = String.format("UPDATE glass SET `name` = ?, `description` = ?, `price_per_square_meter` = ? WHERE id = ?;");

        PreparedStatement statement = null;

        try {

            Connection connection = getConnection();

            try {

                statement = connection.prepareStatement(update);
                statement.setString(1, glass.getName());
                statement.setString(2, glass.getDescription());
                statement.setBigDecimal(3, glass.getPricePerSquareMeter());
                statement.setInt(4, glass.getID());

                int updated = statement.executeUpdate();

                if (updated < 1) {
                    throw new UnknownGlassException(glass);
                }

                connection.commit();

            } catch (SQLException | UnknownGlassException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }

        } catch (UnknownGlassReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Deletes the {@link Glass} referenced by the provided
     * {@link GlassReference} from the {@link GlassDAO}.
     *
     * @param glassReference The {@link GlassReference} referencing the
     * {@link Glass} to delete from the {@link GlassDAO}.
     * @throws UnknownGlassReferenceException When the referenced {@link Glass}
     * doesn't exist in the {@link GlassDAO}.
     */
    @Override
    public void deleteGlass(GlassReference glassReference) throws UnknownGlassReferenceException {
        try {

            Connection connection = getConnection();

            String delete = String.format("DELETE FROM glass WHERE `id` = ?;");

            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setInt(1, glassReference.getID());
                int updated = statement.executeUpdate();

                if (updated == 0) {
                    throw new UnknownGlassReferenceException(glassReference);
                }

                connection.commit();

            } catch (SQLException | UnknownGlassReferenceException e) {
                connection.rollback();
                throw e;
            }

        } catch (UnknownGlassReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Uses the provided {@link GlassBuilder} to insert a new {@link Glass} into
     * the {@link GlassDAO}.
     *
     * @param builder The {@link GlassBuilder} to use to insert the new
     * {@link Glass}.
     * @return The newly created {@link Glass}.
     */
    @Override
    public Glass insertGlass(GlassBuilder builder) {

        String glassSQL = String.format("INSERT INTO glass (name, description, price_per_square_meter) VALUES (?, ?, ?);");

        PreparedStatement glassStatement = null;

        try {

            Connection connection = getConnection();

            try {

                glassStatement = connection.prepareStatement(glassSQL, Statement.RETURN_GENERATED_KEYS);
                glassStatement.setString(1, builder.getName());
                glassStatement.setString(2, builder.getDescription());
                glassStatement.setBigDecimal(3, builder.getPricePerSquareMeter());
                glassStatement.executeUpdate();

                connection.commit();

                ResultSet insertedIndex = glassStatement.getGeneratedKeys();
                insertedIndex.next();
                return getGlass(GlassReference.of(insertedIndex.getInt(1)));

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (glassStatement != null) {
                    glassStatement.close();
                }
            }

        } catch (SQLException | UnknownGlassReferenceException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates a new {@link Glass} from the provided {@link ResultSet}.
     *
     * @param results The {@link ResultSet}.
     * @return The newly created {@link Glass}.
     * @throws SQLException When something goes wrong.
     */
    private Glass fromResultSet(ResultSet results) throws SQLException {
        return new Glass(
                results.getInt("id"),
                results.getString("name"),
                results.getString("description"),
                results.getBigDecimal("price_per_square_meter")
        );
    }
}
