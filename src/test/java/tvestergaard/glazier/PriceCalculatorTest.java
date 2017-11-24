package tvestergaard.glazier;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

public class PriceCalculatorTest {

    public PriceCalculatorTest() {
    }

    /**
     * Test of calculatePrice method, of class PriceCalculator.
     */
    @Test
    public void testCalculatePrice() throws Exception {
        Frame frame = new Frame(0, null, null, BigDecimal.valueOf(100));
        Glass glass = new Glass(0, null, null, BigDecimal.valueOf(300));
        BigDecimal widthMeter = BigDecimal.valueOf(1000);
        BigDecimal heightMeter = BigDecimal.valueOf(1600);
        PriceCalculator instance = new PriceCalculator();
        BigDecimal result = instance.calculatePrice(frame, glass, widthMeter, heightMeter);
        assertEquals(1000.0, result.doubleValue(), 0.0001);
    }

    @Test(expected = IllegalDimensionsException.class)
    public void testCalculatePriceZeroWidth() throws Exception {
        Frame frame = new Frame(0, null, null, BigDecimal.valueOf(100));
        Glass glass = new Glass(0, null, null, BigDecimal.valueOf(300));
        BigDecimal widthMeter = BigDecimal.valueOf(0);
        BigDecimal heightMeter = BigDecimal.valueOf(1000);
        PriceCalculator instance = new PriceCalculator();
        instance.calculatePrice(frame, glass, widthMeter, heightMeter);
    }

    @Test(expected = IllegalDimensionsException.class)
    public void testCalculatePriceZeroHeight() throws Exception {
        Frame frame = new Frame(0, null, null, BigDecimal.valueOf(100));
        Glass glass = new Glass(0, null, null, BigDecimal.valueOf(300));
        BigDecimal widthMeter = BigDecimal.valueOf(1000);
        BigDecimal heightMeter = BigDecimal.valueOf(0);
        PriceCalculator instance = new PriceCalculator();
        instance.calculatePrice(frame, glass, widthMeter, heightMeter);
    }

    @Test(expected = IllegalDimensionsException.class)
    public void testCalculatePriceNegativeWidth() throws Exception {
        Frame frame = new Frame(0, null, null, BigDecimal.valueOf(100));
        Glass glass = new Glass(0, null, null, BigDecimal.valueOf(300));
        BigDecimal widthMeter = BigDecimal.valueOf(-345);
        BigDecimal heightMeter = BigDecimal.valueOf(1000);
        PriceCalculator instance = new PriceCalculator();
        instance.calculatePrice(frame, glass, widthMeter, heightMeter);
    }

    @Test(expected = IllegalDimensionsException.class)
    public void testCalculatePriceNegativeHeight() throws Exception {
        Frame frame = new Frame(0, null, null, BigDecimal.valueOf(100));
        Glass glass = new Glass(0, null, null, BigDecimal.valueOf(300));
        BigDecimal widthMeter = BigDecimal.valueOf(1000);
        BigDecimal heightMeter = BigDecimal.valueOf(-34);
        PriceCalculator instance = new PriceCalculator();
        instance.calculatePrice(frame, glass, widthMeter, heightMeter);
    }
}
