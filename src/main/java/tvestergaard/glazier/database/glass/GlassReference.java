package tvestergaard.glazier.database.glass;

public class GlassReference {

    private final int reference;

    public GlassReference(int reference) {
        this.reference = reference;
    }

    public static GlassReference of(int reference) {
        return new GlassReference(reference);
    }

    public final int getID() {
        return reference;
    }

}
