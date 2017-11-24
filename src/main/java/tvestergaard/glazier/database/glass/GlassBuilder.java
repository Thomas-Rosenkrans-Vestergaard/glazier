package tvestergaard.glazier.database.glass;

import java.math.BigDecimal;

/**
 *
 * @author Thomas
 */
public class GlassBuilder {

    /**
     * The name of the {@link Glass} to build.
     */
    private String name;

    /**
     * The description of the {@link Glass} to build.
     */
    private String description;

    /**
     * The price per square meter of {@link Glass} to build.
     */
    private BigDecimal pricePerSquareMeters;

    /**
     * Returns the name of the {@link Glass} to build.
     *
     * @return The name of the {@link Glass} to build.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Glass} to build.
     *
     * @param name The name to set.
     * @return Whether or not the name was set.
     */
    public boolean setName(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * Returns the description of the {@link Glass} to build.
     *
     * @return The description of the {@link Glass} to build.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the {@link Glass} to build.
     *
     * @param description The description to set.
     * @return Whether or not the description was set.
     */
    public boolean setDescription(String description) {

        if (description == null || description.length() == 0) {
            return false;
        }

        this.description = description;
        return true;
    }

    /**
     * Returns the price per square meter of {@link Glass} to build.
     *
     * @return The price per square meter of {@link Glass} to build.
     */
    public BigDecimal getPricePerSquareMeter() {
        return pricePerSquareMeters;
    }

    /**
     * Sets the price per square meter of {@link Glass} to build.
     *
     * @param price The price to set.
     * @return Whether or not the price was set.
     */
    public boolean setPricePerSquareMeter(BigDecimal price) {

        if (price.doubleValue() < 1) {
            return false;
        }

        this.pricePerSquareMeters = price;
        return true;
    }
}
