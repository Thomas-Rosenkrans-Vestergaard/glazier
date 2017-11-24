package tvestergaard.glazier.database.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tvestergaard.glazier.database.AbstractMysqlDAO;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;

public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO {

    /**
     * Creates a new {@link MysqlOrderDAO}.
     *
     * @param source The {@link MysqlDataSource} from which to access the data
     * manipulated by the {@link MysqlOrderDAO}.
     */
    public MysqlOrderDAO(MysqlDataSource source) {
        super(source);
    }

    /**
     * Retrieves and returns the {@link Order} referenced by the provided
     * {@link OrderReference} from the {@link OrderDAO}.
     *
     * @param orderReference The {@link OrderReference} referencing the
     * {@link Order} to retrieve return.
     * @return The {@link Order} referenced by the provided
     * {@link OrderReference}.
     * @throws UnknownOrderReferenceException When the provided
     * {@link OrderReference} doesn't reference an existing {@link Order}.
     */
    @Override
    public Order getOrder(OrderReference orderReference) throws UnknownOrderReferenceException {
        try {

            PreparedStatement statement = null;
            ResultSet results = null;

            try {
                String select = "SELECT * FROM orders INNER JOIN frames ON orders.frame = frames.id INNER JOIN glass ON orders.glass = glass.id WHERE orders.id = ?";
                System.out.println(select);
                statement = getConnection().prepareStatement(select);
                statement.setInt(1, orderReference.getID());
                results = statement.executeQuery();

                if (!results.first()) {
                    throw new UnknownOrderReferenceException(orderReference);
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

        } catch (UnknownOrderReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieves and returns all the {@link Order}s from the {@link OrderDAO}.
     *
     * @return The compelete list of {@link Order}s.
     */
    @Override
    public List<Order> getOrders() {

        try {

            PreparedStatement statement = null;
            ResultSet orders = null;

            try {
                String select = "SELECT * FROM orders INNER JOIN frames ON orders.frame = frames.id INNER JOIN glass ON orders.glass = glass.id";
                statement = getConnection().prepareStatement(select);
                orders = statement.executeQuery();
                List<Order> results = new ArrayList<>();

                while (orders.next()) {
                    results.add(fromResultSet(orders));
                }

                return results;

            } finally {
                if (statement != null) {
                    statement.close();
                }
                if (orders != null) {
                    orders.close();
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Saves any changes to the provided {@link Order} to the {@link OrderDAO}.
     *
     * @param order The {@link Order} to update.
     * @throws UnknownOrderException When the provided {@link Order} doesn't
     * exist in the {@link OrderDAO}.
     */
    @Override
    public void updateOrder(Order order) throws UnknownOrderException {

        String update = "UPDATE orders SET frame = ?, glass = ?, width_mm = ?, height_mm =?, customer_name = ?, customer_address = ?, customer_zip = ?, customer_city = ?, customer_email = ?, customer_phone = ? WHERE id = ?";

        try {

            Connection connection = getConnection();
            PreparedStatement statement = null;

            try {

                statement = connection.prepareStatement(update);
                statement.setInt(1, order.getFrame().getID());
                statement.setInt(2, order.getGlass().getID());
                statement.setInt(3, order.getWidthMillimeters());
                statement.setInt(4, order.getHeightMillimeters());
                statement.setString(5, order.getCustomerName());
                statement.setString(6, order.getCustomerAddress());
                statement.setString(7, order.getCustomerZip());
                statement.setString(8, order.getCustomerCity());
                statement.setString(9, order.getCustomerEmail());
                statement.setString(10, order.getCustomerPhone());
                statement.setInt(11, order.getID());

                int updated = statement.executeUpdate();

                if (updated < 1) {
                    throw new UnknownOrderException(order);
                }

                connection.commit();

            } catch (SQLException | UnknownOrderException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }

        } catch (UnknownOrderException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Deletes the {@link Order} referenced by the provided
     * {@link OrderReference} from the {@link OrderDAO}.
     *
     * @param orderReference The {@link OrderReference} referencing the
     * {@link Order} to delete from the {@link OrderDAO}.
     * @throws UnknownOrderReferenceException When the referenced {@link Order}
     * doesn't exist in the {@link OrderDAO}.
     */
    @Override
    public void deleteOrder(OrderReference orderReference) throws UnknownOrderReferenceException {
        try {

            Connection connection = getConnection();

            String delete = String.format("DELETE FROM orders WHERE id = ?;");

            PreparedStatement statement = null;

            try {
                statement = connection.prepareStatement(delete);
                statement.setInt(1, orderReference.getID());
                int updated = statement.executeUpdate();

                if (updated == 0) {
                    throw new UnknownOrderReferenceException(orderReference);
                }

                connection.commit();

            } catch (SQLException | UnknownOrderReferenceException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }

        } catch (UnknownOrderReferenceException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Uses the provided {@link OrderBuilder} to insert a new {@link Order} into
     * the {@link OrderDAO}.
     *
     * @param builder The {@link OrderBuilder} to use to insert the new
     * {@link Order}.
     * @return The newly created {@link Order}.
     * @throws UnknownFrameReferenceException When the {@link Frame} inserted
     * with the {@link Order} doesn't exist.
     * @throws UnknownGlassReferenceException WHen the {@link Glass} inserted
     * with the {@link Order} doesn't exist.
     */
    @Override
    public Order insertOrder(OrderBuilder builder) throws UnknownFrameReferenceException, UnknownGlassReferenceException {

        String update = "INSERT INTO orders (frame, glass, width_mm, height_mm, customer_name, customer_address, customer_zip, customer_city, customer_email, customer_phone) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection connection = getConnection();
            PreparedStatement statement = null;

            try {

                statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, builder.getFrame().getID());
                statement.setInt(2, builder.getGlass().getID());
                statement.setInt(3, builder.getWidthMillimeters());
                statement.setInt(4, builder.getHeightMillimeters());
                statement.setString(5, builder.getCustomerName());
                statement.setString(6, builder.getCustomerAddress());
                statement.setString(7, builder.getCustomerZip());
                statement.setString(8, builder.getCustomerCity());
                statement.setString(9, builder.getCustomerEmail());
                statement.setString(10, builder.getCustomerPhone());

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.first();

                connection.commit();

                return getOrder(OrderReference.of(generatedKeys.getInt(1)));

            } catch (SQLException | UnknownOrderReferenceException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }

        } catch (SQLException | UnknownOrderReferenceException e) {
            throw new IllegalStateException(e);
        }
    }

    private Order fromResultSet(ResultSet results) throws SQLException {
        return new Order(
                results.getInt("id"),
                results.getInt("width_mm"),
                results.getInt("height_mm"),
                new Frame(
                        results.getInt("frames.id"),
                        results.getString("frames.name"),
                        results.getString("frames.description"),
                        results.getBigDecimal("price_per_meter")
                ),
                new Glass(
                        results.getInt("glass.id"),
                        results.getString("glass.name"),
                        results.getString("glass.description"),
                        results.getBigDecimal("price_per_square_meter")
                ),
                results.getString("customer_name"),
                results.getString("customer_address"),
                results.getString("customer_zip"),
                results.getString("customer_city"),
                results.getString("customer_email"),
                results.getString("customer_phone")
        );
    }
}
