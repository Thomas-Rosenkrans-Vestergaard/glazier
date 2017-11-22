package tvestergaard.glazier.database.glass;

import java.util.List;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.FrameReference;
import tvestergaard.glazier.database.frames.UnknownFrameException;
import tvestergaard.glazier.database.frames.UnknownFrameReferenceException;

public interface GlassDAO {

    Glass getGlass(GlassReference glassReference) throws UnknownGlassReferenceException;

    List<Glass> getGlasses();

    void updateGlass(Glass glass) throws UnknownGlassException;

    void deleteGlass(GlassReference glassReference) throws UnknownGlassReferenceException;
}
