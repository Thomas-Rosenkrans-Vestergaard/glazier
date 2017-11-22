package tvestergaard.glazier.database.glass;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tvestergaard.glazier.database.AbstractMysqlDAO;
import tvestergaard.glazier.database.frames.UnknownFrameException;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;

public class MysqlGlassDAO extends AbstractMysqlDAO implements GlassDAO {

    public MysqlGlassDAO(MysqlDataSource source) {
        super(source);
    }

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

    @Override
    public void updateGlass(Glass glass) throws UnknownGlassException {
        String update = String.format("UPDATE glass SET `name` = ?, `description` = ?, `price_per_m2` = ? WHERE id = ?;");

        PreparedStatement statement = null;

        try {

            Connection connection = getConnection();

            try {

                statement = connection.prepareStatement(update);
                statement.setString(1, glass.getName());
                statement.setString(2, glass.getDescription());
                statement.setInt(3, glass.getPricePerSquareMeter());
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

    @Override
    public void deleteGlass(GlassReference glassReference) throws UnknownGlassReferenceException {
        try {

            Connection connection = getConnection();

            String delete = String.format("DELETE FROM glass WHERE `id` = ?;");

            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(delete);
                statement.setInt(1, glassReference.getID());
                int updated = statement.executeUpdate();

                if (updated == 0) {
                    throw new UnknownGlassReferenceException(glassReference);
                }

                connection.commit();

            } catch (SQLException | UnknownGlassReferenceException e) {
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
   
    private Glass fromResultSet(ResultSet results) throws SQLException {
        return new Glass(
                results.getInt("id"),
                results.getString("name"),
                results.getString("description"),
                results.getInt("price_per_m2")
        );
    }
}
