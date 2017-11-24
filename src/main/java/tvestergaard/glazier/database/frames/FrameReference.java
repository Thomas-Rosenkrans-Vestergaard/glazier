package tvestergaard.glazier.database.frames;

/**
 * References some {@link Frame} using an internal id.
 *
 * @author Thomas
 */
public class FrameReference {

    /**
     * The id of the referenced {@link Frame}.
     */
    private final int frameID;

    /**
     * Creates a new {@link FrameReference}.
     *
     * @param frameID The id of the {@link Frame} referenced.
     */
    public FrameReference(int frameID) {
        this.frameID = frameID;
    }

    /**
     * Creates a new {@link FrameReference}. Provides a more concise method for
     * creating instances of {@link FrameReference}.
     *
     * @param frameID The id of the {@link Frame} referenced.
     * @return The newly created {@link FrameReference}.
     */
    public static FrameReference of(int frameID) {
        return new FrameReference(frameID);
    }

    /**
     * Returns the id of the referenced {@link Frame}.
     *
     * @return The id of the referenced {@link Frame}.
     */
    public int getID() {
        return frameID;
    }
}
