/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.database.glass;

import java.math.BigDecimal;

/**
 *
 * @author Thomas
 */
public class GlassBuilder {

    private String name;
    private String description;
    private BigDecimal pricePerSquareMeter;

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }

        this.name = name;
        return true;
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {

        if (description == null || description.length() == 0) {
            return false;
        }

        this.description = description;
        return true;
    }

    public BigDecimal getPricePerSquareMeter() {
        return pricePerSquareMeter;
    }

    public boolean setPricePerSquareMeter(BigDecimal price) {

        if (price.doubleValue() < 1) {
            return false;
        }

        this.pricePerSquareMeter = price;
        return true;
    }
}
