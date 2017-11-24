package tvestergaard.glazier.database.frames;

/**
 * Thrown when an {@link FrameReference} couldn't be found.
 *
 * @author Thomas
 */
public class UnknownFrameReferenceException extends Exception {

    /**
     * The unknown {@link FrameReference}.
     */
    private final FrameReference unknownFrameReference;

    /**
     * Creates a new {@link UnknownFrameReferenceException}.
     *
     * @param unknownFrameReference The unknown {@link FrameReference}.
     */
    public UnknownFrameReferenceException(FrameReference unknownFrameReference) {
        this.unknownFrameReference = unknownFrameReference;
    }

    /**
     * Returns the unknown {@link FrameReference}.
     *
     * @return The unknown {@link FrameReference}.
     */
    public FrameReference getUnknownFrameReference() {
        return unknownFrameReference;
    }
}
