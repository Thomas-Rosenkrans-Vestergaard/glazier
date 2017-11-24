package tvestergaard.glazier.database.frames;

import java.util.List;

public interface FrameDAO {

    /**
     * Retrieves and returns the {@link Frame} referenced by the provided
     * {@link FrameReference} from the {@link FrameDAO}.
     *
     * @param frameReference The {@link FrameReference} referencing the
     * {@link Frame} to retrieve return.
     * @return The {@link Frame} referenced by the provided
     * {@link FrameReference}.
     * @throws UnknownFrameReferenceException When the provided
     * {@link FrameReference} doesn't reference an existing {@link Frame}.
     */
    Frame getFrame(FrameReference frameReference) throws UnknownFrameReferenceException;

    /**
     * Retrieves and returns all the {@link Frame}s from the {@link FrameDAO}.
     *
     * @return The compelete list of {@link Frame}s.
     */
    List<Frame> getFrames();

    /**
     * Saves any changes to the provided {@link Frame} to the {@link FrameDAO}.
     *
     * @param frame The {@link Frame} to update.
     * @throws UnknownFrameException When the provided {@link Frame} doesn't
     * exist in the {@link FrameDAO}.
     */
    void updateFrame(Frame frame) throws UnknownFrameException;

    /**
     * Deletes the {@link Frame} referenced by the provided
     * {@link FrameReference} from the {@link FrameDAO}.
     *
     * @param frameReference The {@link FrameReference} referencing the
     * {@link Frame} to delete from the {@link FrameDAO}.
     * @throws UnknownFrameReferenceException When the referenced {@link Frame}
     * doesn't exist in the {@link FrameDAO}.
     */
    void deleteFrame(FrameReference frameReference) throws UnknownFrameReferenceException;

    /**
     * Uses the provided {@link FrameBuilder} to insert a new {@link Frame} into
     * the {@link FrameDAO}.
     *
     * @param builder The {@link FrameBuilder} to use to insert the new
     * {@link Frame}.
     * @return The newly created {@link Frame}.
     */
    Frame insertFrame(FrameBuilder builder);
}
