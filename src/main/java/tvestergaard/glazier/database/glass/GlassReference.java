package tvestergaard.glazier.database.glass;

/**
 * References some {@link Glass} using an internal id.
 *
 * @author Thomas
 */
public class GlassReference {

    /**
     * The id of the referenced {@link Glass}.
     */
    private final int glassID;

    /**
     * Creates a new {@link GlassReference}.
     *
     * @param glassID The id of the {@link Glass} referenced.
     */
    public GlassReference(int glassID) {
        this.glassID = glassID;
    }

    /**
     * Creates a new {@link GlassReference}. Provides a more concise method for
     * creating instances of {@link GlassReference}.
     *
     * @param glassID The id of the {@link Glass} referenced.
     * @return The newly created {@link GlassReference}.
     */
    public static GlassReference of(int glassID) {
        return new GlassReference(glassID);
    }

    /**
     * Returns the id of the referenced {@link Glass}.
     *
     * @return The id of the referenced {@link Glass}.
     */
    public int getID() {
        return glassID;
    }
}
