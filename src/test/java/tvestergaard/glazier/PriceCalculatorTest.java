/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier;

import org.junit.Test;
import static org.junit.Assert.*;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

/**
 *
 * @author Skole
 */
public class PriceCalculatorTest {

    public PriceCalculatorTest() {
    }

    /**
     * Test of calculatePrice method, of class PriceCalculator.
     */
    @Test
    public void testCalculatePrice() {
        Frame frame = new Frame(0, null, null, 100);
        Glass glass = new Glass(0, null, null, 300);
        int widthMM = 1000;
        int heightMM = 1600;
        PriceCalculator instance = new PriceCalculator();
        double result = instance.calculatePrice(frame, glass, widthMM, heightMM);
        assertEquals(1000.0, result, 0.0001);
    }

}
