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
public class UnknownGlassException extends UnknownGlassReferenceException {

    private Glass unknownGlass;

    public UnknownGlassException(Glass unknownGlass) {
        super(unknownGlass);
        this.unknownGlass = unknownGlass;
    }

    public Glass getUnknownGlass() {
        return unknownGlass;
    }
}
