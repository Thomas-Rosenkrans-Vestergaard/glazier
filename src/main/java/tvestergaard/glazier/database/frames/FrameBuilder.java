/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.database.frames;

import java.math.BigDecimal;

/**
 *
 * @author Thomas
 */
public class FrameBuilder {

    /**
     * The name of the {@link Frame} to build.
     */
    private String name;

    /**
     * The description of the {@link Frame} to build.
     */
    private String description;

    /**
     * The price per meters of {@link Frame} to build.
     */
    private BigDecimal pricePerMeters;

    /**
     * Returns the name of the {@link Frame} to build.
     *
     * @return The name of the {@link Frame} to build.
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Frame} to build.
     *
     * @param name The name to set.
     * @return Whether or not the name was set.
     */
    public final boolean setName(String name) {

        if (name == null || name.length() == 0) {
            return false;
        }

        this.name = name;
        return true;
    }

    /**
     * Returns the description of the {@link Frame} to build.
     *
     * @return The description of the {@link Frame} to build.
     */
    public final String getDescription() {

        return description;
    }

    /**
     * Sets the description of the {@link Frame} to build.
     *
     * @param description The description to set.
     * @return Whether or not the description was set.
     */
    public final boolean setDescription(String description) {

        if (description == null || description.length() == 0) {
            return false;
        }

        this.description = description;
        return true;
    }

    /**
     * Returns the price per meters of {@link Frame} to build.
     *
     * @return The price per meters of {@link Frame} to build
     */
    public final BigDecimal getPricePerMeter() {
        return pricePerMeters;
    }

    /**
     * Sets the price per meters of {@link Frame} to build
     *
     * @param price The price per meter to set.
     * @return Whether or not the price per meter was set.
     */
    public final boolean setPricePerMeter(BigDecimal price) {

        if (price.doubleValue() < 0) {
            return false;
        }

        this.pricePerMeters = price;
        return true;
    }
}
