package tvestergaard.glazier.database.frames;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tvestergaard.glazier.database.AbstractMysqlDAO;

public class MysqlFrameDAO extends AbstractMysqlDAO implements FrameDAO {

    public MysqlFrameDAO(MysqlDataSource source) {
        super(source);
    }

    @Override
    public Frame getFrame(FrameReference frameReference) throws UnknownFrameReferenceException {

        try {

            PreparedStatement statement = null;
            ResultSet results = null;

            try {
                String select = "SELECT * FROM frames WHERE id = ?";
                statement = getConnection().prepareStatement(select);
                statement.setInt(1, frameReference.getID());
                results = statement.executeQuery();

                if (!results.first()) {
                    throw new UnknownFrameReferenceException(frameReference);
                }

                return fromResultSet(results);

            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (statement != null) {
                    results.close();
                }
            }

        } catch (UnknownFrameReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Frame> getFrames() {

        try {

            PreparedStatement statement = null;
            ResultSet frames = null;

            try {
                String select = "SELECT * FROM frames";
                statement = getConnection().prepareStatement(select);
                frames = statement.executeQuery();
                List<Frame> results = new ArrayList<>();

                while (frames.next()) {
                    results.add(fromResultSet(frames));
                }

                return results;

            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (frames != null) {
                    frames.close();
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void updateFrame(Frame frame) throws UnknownFrameException {
        String update = String.format("UPDATE frames SET `name` = ?, `description` = ?, `price_per_meter` = ? WHERE id = ?;");

        PreparedStatement statement = null;

        try {

            Connection connection = getConnection();

            try {

                statement = connection.prepareStatement(update);
                statement.setString(1, frame.getName());
                statement.setString(2, frame.getDescription());
                statement.setBigDecimal(3, frame.getPricePerMeter());
                statement.setInt(4, frame.getID());

                int updated = statement.executeUpdate();
                if (updated < 1) {
                    throw new UnknownFrameException(frame);
                }

                connection.commit();

            } catch (SQLException | UnknownFrameException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }

        } catch (UnknownFrameReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteFrame(FrameReference frameReference) throws UnknownFrameReferenceException {

        try {

            Connection connection = getConnection();

            String delete = String.format("DELETE FROM frames WHERE `id` = ?;");

            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setInt(1, frameReference.getID());
                int updated = statement.executeUpdate();

                if (updated == 0) {
                    throw new UnknownFrameReferenceException(frameReference);
                }

                connection.commit();

            } catch (SQLException | UnknownFrameReferenceException e) {
                connection.rollback();
                throw e;
            }

        } catch (UnknownFrameReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Frame insertFrame(FrameBuilder builder) {

        String frameSQL = String.format("INSERT INTO frames (name, description, price_per_meter) VALUES (?, ?, ?);");

        PreparedStatement frameStatement = null;

        try {

            Connection connection = getConnection();

            try {

                frameStatement = connection.prepareStatement(frameSQL, Statement.RETURN_GENERATED_KEYS);
                frameStatement.setString(1, builder.getName());
                frameStatement.setString(2, builder.getDescription());
                frameStatement.setBigDecimal(3, builder.getPricePerMeter());
                frameStatement.executeUpdate();
                
                ResultSet insertedIndex = frameStatement.getGeneratedKeys();
                insertedIndex.next();

                connection.commit();

                return getFrame(FrameReference.of(insertedIndex.getInt(1)));

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (frameStatement != null) {
                    frameStatement.close();
                }
            }

        } catch (SQLException | UnknownFrameReferenceException e) {
            throw new IllegalStateException(e);
        }
    }

    private Frame fromResultSet(ResultSet results) throws SQLException {
        return new Frame(
                results.getInt("id"),
                results.getString("name"),
                results.getString("description"),
                results.getBigDecimal("price_per_meter")
        );
    }
}
