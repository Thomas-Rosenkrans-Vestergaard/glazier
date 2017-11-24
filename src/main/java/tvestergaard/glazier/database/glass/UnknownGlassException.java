package tvestergaard.glazier.database.glass;

/**
 * Thrown when a {@link Glass} couldn't be found.
 *
 * @author Thomas
 */
public class UnknownGlassException extends UnknownGlassReferenceException {

    /**
     * The unknown {@link Glass}.
     */
    private final Glass unknownGlass;

    /**
     * Creates a new {@link UnknownGlassException}.
     *
     * @param unknownGlass The unknown {@link Glass}.
     */
    public UnknownGlassException(Glass unknownGlass) {
        super(unknownGlass);
        this.unknownGlass = unknownGlass;
    }

    /**
     * Returns the unknown {@link Glass}.
     *
     * @return The unknown {@link Glass}.
     */
    public Glass getUnknownGlass() {
        return unknownGlass;
    }
}
