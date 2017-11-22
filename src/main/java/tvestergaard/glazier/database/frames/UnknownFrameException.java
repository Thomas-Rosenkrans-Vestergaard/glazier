package tvestergaard.glazier.database.frames;

public class UnknownFrameException extends UnknownFrameReferenceException {

    private final Frame unknownFrame;

    public UnknownFrameException(Frame unknownFrame) {
        super(unknownFrame);
        this.unknownFrame = unknownFrame;
    }

    public Frame getUnknownFrame() {
        return unknownFrame;
    }
}
