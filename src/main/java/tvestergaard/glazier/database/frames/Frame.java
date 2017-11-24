package tvestergaard.glazier.database.frames;

import java.math.BigDecimal;
import java.util.Objects;

public final class Frame extends FrameReference {

    /**
     * The name of the {@link Frame}.
     */
    private String name;

    /**
     * The description of the {@link Frame}.
     */
    private String description;

    /**
     * The price per meters of {@link Frame}.
     */
    private BigDecimal pricePerMeters;

    /**
     * Creates a new {@link Frame}.
     *
     * @param id The id of the {@link Frame}.
     * @param name The name of the {@link Frame}.
     * @param description The description of the {@link Frame}.
     * @param pricePerMillimeter The price per meters of {@link Frame}.
     */
    public Frame(final int id, String name, String description, BigDecimal pricePerMillimeter) {
        super(id);
        this.name = name;
        this.description = description;
        this.pricePerMeters = pricePerMillimeter;
    }

    /**
     * Returns the name of the {@link Frame}.
     *
     * @return The name of the {@link Frame}.
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Frame}.
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
     * Returns the description of the {@link Frame}.
     *
     * @return The description of the {@link Frame}.
     */
    public final String getDescription() {

        return description;
    }

    /**
     * Sets the description of the {@link Frame}.
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
     * Returns the price per meters of {@link Frame}.
     *
     * @return The price per meters of {@link Frame}
     */
    public final BigDecimal getPricePerMeter() {
        return pricePerMeters;
    }

    /**
     * Sets the price per meters of {@link Frame}
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

    /**
     * Compares other objects for equallity. Other objects are only equal when
     * they are an instance of {@link Frame} with the same <code>id</code> as
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
        final Frame other = (Frame) obj;
        if (!Objects.equals(this.getID(), other.getID())) {
            return false;
        }
        return true;
    }
}
