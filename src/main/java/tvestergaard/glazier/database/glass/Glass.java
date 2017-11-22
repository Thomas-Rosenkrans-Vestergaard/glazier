package tvestergaard.glazier.database.glass;

import java.util.Objects;
import tvestergaard.glazier.database.frames.Frame;

public final class Glass extends GlassReference {

    private String name;
    private String description;
    private int pricePerSquareMeter;

    public Glass(int id, String name, String description, int pricePerSquareMeter) {
        super(id);
        this.name = name;
        this.description = description;
        this.pricePerSquareMeter = pricePerSquareMeter;
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

    public int getPricePerSquareMeter() {
        return pricePerSquareMeter;
    }

    public boolean setPricePerSquareMeter(int pricePerSquareMeter) {

        if (pricePerSquareMeter < 1) {
            return false;
        }

        this.pricePerSquareMeter = pricePerSquareMeter;
        return true;
    }

    public boolean setPricePerSquareMeter(String pricePerSquareMeter) {
        try {
            return setPricePerSquareMeter(Integer.parseInt(pricePerSquareMeter));
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
        final Glass other = (Glass) obj;
        if (!Objects.equals(this.getID(), other.getID())) {
            return false;
        }
        return true;
    }
}
