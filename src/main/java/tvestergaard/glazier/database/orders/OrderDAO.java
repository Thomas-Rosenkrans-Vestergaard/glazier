package tvestergaard.glazier.database.orders;

import java.util.List;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;
import tvestergaard.glazier.database.glass.Glass;
import tvestergaard.glazier.database.glass.UnknownGlassReferenceException;

public interface OrderDAO {
    
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
    Order getOrder(OrderReference orderReference) throws UnknownOrderReferenceException;

    /**
     * Retrieves and returns all the {@link Order}s from the {@link OrderDAO}.
     *
     * @return The compelete list of {@link Order}s.
     */
    List<Order> getOrders();

    /**
     * Saves any changes to the provided {@link Order} to the {@link OrderDAO}.
     *
     * @param order The {@link Order} to update.
     * @throws UnknownOrderException When the provided {@link Order} doesn't
     * exist in the {@link OrderDAO}.
     */
    void updateOrder(Order order) throws UnknownOrderException;

    /**
     * Deletes the {@link Order} referenced by the provided
     * {@link OrderReference} from the {@link OrderDAO}.
     *
     * @param orderReference The {@link OrderReference} referencing the
     * {@link Order} to delete from the {@link OrderDAO}.
     * @throws UnknownOrderReferenceException When the referenced {@link Order}
     * doesn't exist in the {@link OrderDAO}.
     */
    void deleteOrder(OrderReference orderReference) throws UnknownOrderReferenceException;

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
    Order insertOrder(OrderBuilder builder)
            throws UnknownFrameReferenceException, UnknownGlassReferenceException;
}
