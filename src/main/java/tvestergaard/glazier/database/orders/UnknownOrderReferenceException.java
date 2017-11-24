package tvestergaard.glazier.database.orders;

/**
 * Thrown when an {@link OrderReference} couldn't be found.
 *
 * @author Thomas
 */
public class UnknownOrderReferenceException extends Exception {

    /**
     * The unknown {@link OrderReference}.
     */
    private final OrderReference unknownOrderReference;

    /**
     * Creates a new {@link UnknownOrderReferenceException}.
     *
     * @param unknownOrderReference The unknown {@link OrderReference}.
     */
    public UnknownOrderReferenceException(OrderReference unknownOrderReference) {
        this.unknownOrderReference = unknownOrderReference;
    }

    /**
     * Returns the unknown {@link OrderReference}.
     *
     * @return The unknown {@link OrderReference}.
     */
    public OrderReference getUnknownOrderReference() {
        return unknownOrderReference;
    }
}
