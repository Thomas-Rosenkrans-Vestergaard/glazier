/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier;

import java.math.BigDecimal;
import java.math.BigInteger;
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
        Frame frame = new Frame(0, null, null, BigDecimal.valueOf(100));
        Glass glass = new Glass(0, null, null, BigDecimal.valueOf(300));
        BigDecimal widthMeter = BigDecimal.valueOf(1000);
        BigDecimal heightMeter = BigDecimal.valueOf(1600);
        PriceCalculator instance = new PriceCalculator();
        BigDecimal result = instance.calculatePrice(frame, glass, widthMeter, heightMeter);
        assertEquals(1000.0, result.doubleValue(), 0.0001);
    }
}
