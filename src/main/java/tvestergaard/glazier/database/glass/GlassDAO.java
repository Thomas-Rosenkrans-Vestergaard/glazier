package tvestergaard.glazier.database.glass;

import java.util.List;

public interface GlassDAO {

    /**
     * Retrieves and returns the {@link Glass} referenced by the provided
     * {@link GlassReference} from the {@link GlassDAO}.
     *
     * @param glassReference The {@link GlassReference} referencing the
     * {@link Glass} to retrieve return.
     * @return The {@link Glass} referenced by the provided
     * {@link GlassReference}.
     * @throws UnknownGlassReferenceException When the provided
     * {@link GlassReference} doesn't reference an existing {@link Glass}.
     */
    Glass getGlass(GlassReference glassReference) throws UnknownGlassReferenceException;

    /**
     * Retrieves and returns all the {@link Glass}es from the {@link GlassDAO}.
     *
     * @return The compelete list of {@link Glass}es.
     */
    List<Glass> getGlasses();

    /**
     * Saves any changes to the provided {@link Glass} to the {@link GlassDAO}.
     *
     * @param glass The {@link Glass} to update.
     * @throws UnknownGlassException When the provided {@link Glass} doesn't
     * exist in the {@link GlassDAO}.
     */
    void updateGlass(Glass glass) throws UnknownGlassException;

    /**
     * Deletes the {@link Glass} referenced by the provided
     * {@link GlassReference} from the {@link GlassDAO}.
     *
     * @param glassReference The {@link GlassReference} referencing the
     * {@link Glass} to delete from the {@link GlassDAO}.
     * @throws UnknownGlassReferenceException When the referenced {@link Glass}
     * doesn't exist in the {@link GlassDAO}.
     */
    void deleteGlass(GlassReference glassReference) throws UnknownGlassReferenceException;

    /**
     * Uses the provided {@link GlassBuilder} to insert a new {@link Glass} into
     * the {@link GlassDAO}.
     *
     * @param builder The {@link GlassBuilder} to use to insert the new
     * {@link Glass}.
     * @return The newly created {@link Glass}.
     */
    Glass insertGlass(GlassBuilder builder);
}
