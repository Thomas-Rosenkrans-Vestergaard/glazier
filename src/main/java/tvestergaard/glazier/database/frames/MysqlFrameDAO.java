package tvestergaard.glazier.database.frames;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String update = String.format("UPDATE frames SET `name` = ?, `description` = ?, `price_per_m` = ? WHERE id = ?;");

        PreparedStatement statement = null;

        try {

            Connection connection = getConnection();

            try {

                statement = connection.prepareStatement(update);
                statement.setString(1, frame.getName());
                statement.setString(2, frame.getDescription());
                statement.setInt(3, frame.getPricePerMeter());
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

            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(delete);
                statement.setInt(1, frameReference.getID());
                int updated = statement.executeUpdate();

                if (updated == 0) {
                    throw new UnknownFrameReferenceException(frameReference);
                }

                connection.commit();

            } catch (SQLException | UnknownFrameReferenceException e) {
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

    private Frame fromResultSet(ResultSet results) throws SQLException {
        return new Frame(
                results.getInt("id"),
                results.getString("name"),
                results.getString("description"),
                results.getInt("price_per_m")
        );
    }
}
