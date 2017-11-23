package tvestergaard.glazier.database.orders;

import java.util.List;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.UnknownFrameException;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.UnknownGlassException;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;

public interface OrderDAO {

    Order getOrder(OrderReference orderReference) throws UnknownOrderReferenceException;

    List<Order> getOrders();

    void updateOrder(Order order) throws UnknownOrderException;

    void deleteOrder(OrderReference orderReference) throws UnknownOrderReferenceException;

    Order insertOrder(OrderBuilder builder)
            throws UnknownFrameReferenceException, UnknownGlassReferenceException;
}
