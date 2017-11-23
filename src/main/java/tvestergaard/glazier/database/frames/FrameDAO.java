package tvestergaard.glazier.database.frames;

import java.util.List;

public interface FrameDAO {

    Frame getFrame(FrameReference frameReference) throws UnknownFrameReferenceException;

    List<Frame> getFrames();

    void updateFrame(Frame frame) throws UnknownFrameException;

    void deleteFrame(FrameReference frameReference) throws UnknownFrameReferenceException;

    Frame insertFrame(FrameBuilder builder);
}
