package tvestergaard.glazier.database.glass;

import java.util.List;

public interface GlassDAO {

    Glass getGlass(GlassReference glassReference) throws UnknownGlassReferenceException;

    List<Glass> getGlasses();

    void updateGlass(Glass glass) throws UnknownGlassException;

    void deleteGlass(GlassReference glassReference) throws UnknownGlassReferenceException;
}
