package tvestergaard.glazier.database.orders;

/**
 * References some {@link Order} using an internal id.
 *
 * @author Thomas
 */
public class OrderReference {

    /**
     * The id of the referenced {@link Order}.
     */
    private final int orderID;

    /**
     * Creates a new {@link OrderReference}.
     *
     * @param orderID The id of the {@link Order} referenced.
     */
    public OrderReference(int orderID) {
        this.orderID = orderID;
    }

    /**
     * Creates a new {@link OrderReference}. Provides a more concise method for
     * creating instances of {@link OrderReference}.
     *
     * @param orderID The id of the {@link Order} referenced.
     * @return The newly created {@link OrderReference}.
     */
    public static OrderReference of(int orderID) {
        return new OrderReference(orderID);
    }

    /**
     * Returns the id of the referenced {@link Order}.
     *
     * @return The id of the referenced {@link Order}.
     */
    public int getID() {
        return orderID;
    }
}
