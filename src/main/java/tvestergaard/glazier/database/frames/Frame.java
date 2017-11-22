package tvestergaard.glazier.database.frames;

import java.util.Objects;

public final class Frame extends FrameReference {

    private String name;
    private String description;
    private int pricePerMeter;

    public Frame(final int id, String name, String description, int pricePerMeter) {
        super(id);
        this.name = name;
        this.description = description;
        this.pricePerMeter = pricePerMeter;
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

    public final int getPricePerMeter() {
        return pricePerMeter;
    }

    public final boolean setPricePerMeter(int pricePerMeter) {

        if (pricePerMeter < 0) {
            return false;
        }

        this.pricePerMeter = pricePerMeter;
        return true;
    }

    public final boolean setPricePerMeter(String pricePerMeter) {
        try {
            return setPricePerMeter(Integer.parseInt(pricePerMeter));
        } catch (NumberFormatException e) {
            return false;
        }
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
