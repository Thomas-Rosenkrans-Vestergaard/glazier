/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.database.frames;

/**
 *
 * @author Thomas
 */
public class FrameReference {

    private final int frameID;

    public FrameReference(int frameID) {
        this.frameID = frameID;
    }

    public static FrameReference of(int frameID) {
        return new FrameReference(frameID);
    }

    public int getID() {
        return frameID;
    }
}
