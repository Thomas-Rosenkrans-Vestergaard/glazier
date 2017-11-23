package tvestergaard.glazier.database.glass;

import java.math.BigDecimal;
import java.util.Objects;

public final class Glass extends GlassReference {

    private String name;
    private String description;
    private BigDecimal pricePerSquareMeters;

    public Glass(int id, String name, String description, BigDecimal pricePerSquareMeters) {
        super(id);
        this.name = name;
        this.description = description;
        this.pricePerSquareMeters = pricePerSquareMeters;
    }

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
        return pricePerSquareMeters;
    }

    public boolean setPricePerSquareMeter(BigDecimal price) {

        if (price.doubleValue() < 1) {
            return false;
        }

        this.pricePerSquareMeters = price;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Glass other = (Glass) obj;
        if (!Objects.equals(this.getID(), other.getID())) {
            return false;
        }
        return true;
    }
}
