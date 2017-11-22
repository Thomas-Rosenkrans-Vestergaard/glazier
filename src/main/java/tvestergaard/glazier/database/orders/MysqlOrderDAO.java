package tvestergaard.glazier.database.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tvestergaard.glazier.database.AbstractMysqlDAO;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.UnknownFrameException;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.UnknownGlassException;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;

public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO {

    public MysqlOrderDAO(MysqlDataSource source) {
        super(source);
    }

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
                statement.setInt(3, order.getWidthMM());
                statement.setInt(4, order.getHeightMM());
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

    @Override
    public void deleteOrder(OrderReference orderReference) throws UnknownOrderReferenceException {
        try {

            Connection connection = getConnection();

            String delete = String.format("DELETE FROM orders WHERE `id` = ?;");

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

    @Override
    public Order insertOrder(int widthMM, int heightMM, Frame frame, Glass glass, String customerName, String customerAddress, String customerZip, String customerCity, String customerEmail, String customerPhone) throws UnknownFrameException, UnknownGlassException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                        results.getInt("price_per_m")
                ),
                new Glass(
                        results.getInt("glass.id"),
                        results.getString("glass.name"),
                        results.getString("glass.description"),
                        results.getInt("price_per_m2")
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