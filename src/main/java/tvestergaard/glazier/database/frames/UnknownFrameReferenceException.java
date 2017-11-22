package tvestergaard.glazier.database.frames;

public class UnknownFrameReferenceException extends Exception {

    private final FrameReference unknownFrameReference;

    public UnknownFrameReferenceException(FrameReference unknownFrameReference) {
        this.unknownFrameReference = unknownFrameReference;
    }

    public FrameReference getUnknownFrameReference() {
        return unknownFrameReference;
    }
}
