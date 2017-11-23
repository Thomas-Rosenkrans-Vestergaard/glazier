/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier;

import java.math.BigDecimal;
import java.math.MathContext;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

/**
 *
 * @author Skole
 */
public class PriceCalculator {

    public BigDecimal calculatePrice(Frame frame, Glass glass, BigDecimal widthMeter, BigDecimal heightMeter) {

        BigDecimal price = BigDecimal.ZERO;

        BigDecimal pricePerMeter = frame.getPricePerMeter();
        price = price.add(widthMeter.multiply(BigDecimal.valueOf(2.0)).multiply(pricePerMeter));
        price = price.add(heightMeter.multiply(BigDecimal.valueOf(2.0)).multiply(pricePerMeter));
        
        BigDecimal pricePerSquareMeter = glass.getPricePerSquareMeter();
        price = price.add(pricePerSquareMeter.multiply(widthMeter.multiply(heightMeter)));

        return price;
    }
}
