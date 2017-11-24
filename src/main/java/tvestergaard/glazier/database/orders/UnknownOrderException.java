package tvestergaard.glazier.database.orders;

/**
 * Thrown when a {@link Order} couldn't be found.
 *
 * @author Thomas
 */
public class UnknownOrderException extends UnknownOrderReferenceException {

    /**
     * The unknown {@link Order}.
     */
    private final Order unknownOrder;

    /**
     * Creates a new {@link UnknownOrderException}.
     *
     * @param unknownOrder The unknown {@link Order}.
     */
    public UnknownOrderException(Order unknownOrder) {
        super(unknownOrder);
        this.unknownOrder = unknownOrder;
    }

    /**
     * Returns the unknown {@link Order}.
     *
     * @return The unknown {@link Order}.
     */
    public Order getUnknownOrder() {
        return unknownOrder;
    }
}
