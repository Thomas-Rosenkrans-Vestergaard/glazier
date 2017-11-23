package tvestergaard.glazier.database.frames;

import java.math.BigDecimal;
import java.util.Objects;

public final class Frame extends FrameReference {

    private String name;
    private String description;
    private BigDecimal pricePerMeter;

    public Frame(final int id, String name, String description, BigDecimal pricePerMillimeter) {
        super(id);
        this.name = name;
        this.description = description;
        this.pricePerMeter = pricePerMillimeter;
    }

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
        final Frame other = (Frame) obj;
        if (!Objects.equals(this.getID(), other.getID())) {
            return false;
        }
        return true;
    }

}
