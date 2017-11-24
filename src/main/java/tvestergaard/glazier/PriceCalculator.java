package tvestergaard.glazier;

import java.math.BigDecimal;
import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

/**
 * Calculates the price of a window.
 *
 * @author Skole
 */
public class PriceCalculator {

    /**
     * Calculate the price of a window using the provided parameters.
     *
     * @param frame The {@link Frame} to put on the window.
     * @param glass The {@link Glass} to put on the window.
     * @param widthMillimeters The width of the window in millimeters.
     * @param heightMillimeters The height of the window in millimeters.
     * @return The price of the window.
     */
    public BigDecimal calculatePrice(Frame frame, Glass glass, BigDecimal widthMillimeters, BigDecimal heightMillimeters) throws IllegalDimensionsException {

        int compareWidth = widthMillimeters.compareTo(BigDecimal.ZERO);
        int compareHeight = heightMillimeters.compareTo(BigDecimal.ZERO);
        if (compareWidth == -1 || compareWidth == 0 || compareHeight == -1 || compareHeight == 0) {
            throw new IllegalDimensionsException();
        }

        BigDecimal price = BigDecimal.ZERO;

        BigDecimal widthMeters = widthMillimeters.divide(BigDecimal.valueOf(1000));
        BigDecimal heightMeters = heightMillimeters.divide(BigDecimal.valueOf(1000));

        BigDecimal pricePerMeter = frame.getPricePerMeter();
        price = price.add(widthMeters.multiply(BigDecimal.valueOf(2.0)).multiply(pricePerMeter));
        price = price.add(heightMeters.multiply(BigDecimal.valueOf(2.0)).multiply(pricePerMeter));

        BigDecimal pricePerSquareMeter = glass.getPricePerSquareMeter();
        price = price.add(pricePerSquareMeter.multiply(widthMeters.multiply(heightMeters)));

        return price;
    }
}
