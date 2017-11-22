package tvestergaard.glazier.database.orders;

import java.util.List;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.UnknownFrameException;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.UnknownGlassException;

public interface OrderDAO {

    Order getOrder(OrderReference orderReference) throws UnknownOrderReferenceException;

    List<Order> getOrders();

    void updateOrder(Order order) throws UnknownOrderException;

    void deleteOrder(OrderReference orderReference) throws UnknownOrderReferenceException;

    Order insertOrder(int widthMM, int heightMM, Frame frame, Glass glass, String customerName, String customerAddress, String customerZip, String customerCity, String customerEmail, String customerPhone)
            throws UnknownFrameException, UnknownGlassException;
}
