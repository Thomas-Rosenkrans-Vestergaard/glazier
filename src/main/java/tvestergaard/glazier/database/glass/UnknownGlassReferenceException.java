package tvestergaard.glazier.database.glass;

/**
 * Thrown when an {@link GlassReference} couldn't be found.
 *
 * @author Thomas
 */
public class UnknownGlassReferenceException extends Exception {

    /**
     * The unknown {@link GlassReference}.
     */
    private final GlassReference unknownGlassReference;

    /**
     * Creates a new {@link UnknownGlassReferenceException}.
     *
     * @param unknownGlassReference The unknown {@link GlassReference}.
     */
    public UnknownGlassReferenceException(GlassReference unknownGlassReference) {
        this.unknownGlassReference = unknownGlassReference;
    }

    /**
     * Returns the unknown {@link GlassReference}.
     *
     * @return The unknown {@link GlassReference}.
     */
    public GlassReference getUnknownGlassReference() {
        return unknownGlassReference;
    }
}
