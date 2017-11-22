/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.database.glass;

/**
 *
 * @author Thomas
 */
public class UnknownGlassReferenceException extends Exception {

    private final GlassReference glassReference;

    public UnknownGlassReferenceException(GlassReference glassReference) {
        this.glassReference = glassReference;
    }

    public GlassReference getGlassReference() {
        return glassReference;
    }

}
