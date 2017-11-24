package tvestergaard.glazier.database.frames;

/**
 * Thrown when a {@link Frame} couldn't be found.
 *
 * @author Thomas
 */
public class UnknownFrameException extends UnknownFrameReferenceException {

    /**
     * The unknown {@link Frame}.
     */
    private final Frame unknownFrame;

    /**
     * Creates a new {@link UnknownFrameException}.
     *
     * @param unknownFrame The unknown {@link Frame}.
     */
    public UnknownFrameException(Frame unknownFrame) {
        super(unknownFrame);
        this.unknownFrame = unknownFrame;
    }

    /**
     * Returns the unknown {@link Frame}.
     *
     * @return The unknown {@link Frame}.
     */
    public Frame getUnknownFrame() {
        return unknownFrame;
    }
}
