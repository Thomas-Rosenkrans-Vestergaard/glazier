package tvestergaard.glazier.database.glass;

import java.math.BigDecimal;
import java.util.Objects;

public final class Glass extends GlassReference {

    /**
     * The name of the {@link Glass}.
     */
    private String name;

    /**
     * The description of the {@link Glass}.
     */
    private String description;

    /**
     * The price per square meter of {@link Glass}.
     */
    private BigDecimal pricePerSquareMeters;

    /**
     * Creates a new {@link Glass} instance.
     *
     * @param id The id of the {@link Glass}.
     * @param name The name of the {@link Glass}.
     * @param description The description of the {@link Glass}.
     * @param pricePerSquareMeters The price per square meter of {@link Glass}.
     */
    public Glass(int id, String name, String description, BigDecimal pricePerSquareMeters) {
        super(id);
        this.name = name;
        this.description = description;
        this.pricePerSquareMeters = pricePerSquareMeters;
    }

    /**
     * Returns the name of the {@link Glass}.
     *
     * @return The name of the {@link Glass}.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Glass}.
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
     * Returns the description of the {@link Glass}.
     *
     * @return The description of the {@link Glass}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the {@link Glass}.
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
     * Returns the price per square meter of {@link Glass}.
     *
     * @return The price per square meter of {@link Glass}.
     */
    public BigDecimal getPricePerSquareMeter() {
        return pricePerSquareMeters;
    }

    /**
     * Sets the price per square meter of {@link Glass}.
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

    /**
     * Compares other objects for equallity. Other objects are only equal when
     * they are an instance of {@link Glass} with the same <code>id</code> as
     * <code>this</code>.
     *
     * @param obj The other object.
     * @return True if the other object is considered equal to
     * <code>this</code>.
     */
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
