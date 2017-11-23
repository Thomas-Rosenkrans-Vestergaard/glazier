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

    private String name;
    private String description;
    private BigDecimal pricePerMeter;

    public final String getName() {
        return name;
    }

    public final boolean setName(String name) {

        if (name == null || name.length() == 0) {
            return false;
        }

        this.name = name;
        return true;
    }

    public final String getDescription() {

        return description;
    }

    public final boolean setDescription(String description) {

        if (description == null || description.length() == 0) {
            return false;
        }

        this.description = description;
        return true;
    }

    public final BigDecimal getPricePerMeter() {
        return pricePerMeter;
    }

    public final boolean setPricePerMeter(BigDecimal price) {

        if (price.doubleValue() < 0) {
            return false;
        }

        this.pricePerMeter = price;
        return true;
    }
}
