package tvestergaard.glazier.database.orders;

import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

public final class Order extends OrderReference {

    /**
     * The width of the {@link Order} in millimeters.
     */
    private int widthMillimeters;

    /**
     * The height of the {@link Order} in millimeters.
     */
    private int heightMillimeters;

    /**
     * The {@link Frame} used on the {@link Order}.
     */
    private Frame frame;

    /**
     * The {@link Glass} used on the {@link Order}.
     */
    private Glass glass;

    /**
     * The name of the customer who placed the {@link Order}.
     */
    private String customerName;

    /**
     * The address of the customer who placed the {@link Order}.
     */
    private String customerAddress;

    /**
     * The zip code of the customer who placed the {@link Order}.
     */
    private String customerZip;

    /**
     * The city of the customer who placed the {@link Order}.
     */
    private String customerCity;

    /**
     * The email of the customer who placed the {@link Order}.
     */
    private String customerEmail;

    /**
     * The phone number of the customer who placed the {@link Order}.
     */
    private String customerPhone;

    /**
     *
     * @param id The id of the {@link Order}.
     * @param widthMillimeters The width of the {@link Order} in millimeters.
     * @param heightMillimeters The height of the {@link Order} in millimeters.
     * @param frame The {@link Frame} used on the {@link Order}.
     * @param glass The {@link Glass} used on the {@link Order}.
     * @param customerName The name of the customer who placed the
     * {@link Order}.
     * @param customerAddress The address of the customer who placed the
     * {@link Order}.
     * @param customerZip The zip code of the customer who placed the
     * {@link Order}.
     * @param customerCity The city of the customer who placed the
     * {@link Order}.
     * @param customerEmail The email of the customer who placed the
     * {@link Order}.
     * @param customerPhone The phone number of the customer who placed the
     * {@link Order}.
     */
    public Order(int id, int widthMillimeters, int heightMillimeters, Frame frame, Glass glass, String customerName, String customerAddress, String customerZip, String customerCity, String customerEmail, String customerPhone) {
        super(id);
        this.widthMillimeters = widthMillimeters;
        this.heightMillimeters = heightMillimeters;
        this.frame = frame;
        this.glass = glass;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZip = customerZip;
        this.customerCity = customerCity;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    /**
     * Returns the width of the {@link Order} in millimeters.
     *
     * @return The width of the {@link Order} in millimeters.
     */
    public int getWidthMillimeters() {
        return widthMillimeters;
    }

    /**
     * Sets the width of the {@link Order} in millimeters.
     *
     * @param widthMillimeters The width to set.
     * @return Whether or not the width was set.
     */
    public boolean setWidthMillimeters(int widthMillimeters) {
        if (widthMillimeters < 0) {
            return false;
        }

        this.widthMillimeters = widthMillimeters;
        return true;
    }

    /**
     * Returns the height of the {@link Order} in millimeters.
     *
     * @return The height of the {@link Order} in millimeters.
     */
    public int getHeightMillimeters() {
        return heightMillimeters;
    }

    /**
     * Sets the height of the {@link Order} in millimeters.
     *
     * @param heightMillimeters The height to set.
     * @return Whether or not the height was set.
     */
    public boolean setHeightMillimeters(int heightMillimeters) {
        if (heightMillimeters < 0) {
            return false;
        }

        this.heightMillimeters = heightMillimeters;
        return true;
    }

    /**
     * Returns the {@link Frame} used on the {@link Order}.
     *
     * @return The {@link Frame} used on the {@link Order}.
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Sets The {@link Frame} used on the {@link Order}.
     *
     * @param frame The {@link Frame} to set.
     * @return Whether or not the {@link Frame} was set.
     */
    public boolean setFrame(Frame frame) {
        if (frame == null) {
            return false;
        }

        this.frame = frame;
        return true;
    }

    /**
     * Returns the {@link Glass} used on the {@link Order}.
     *
     * @return The {@link Glass} used on the {@link Order}.
     */
    public Glass getGlass() {
        return glass;
    }

    /**
     * Sets the {@link Glass} used on the {@link Order}.
     *
     * @param glass The {@link Glass} to set.
     * @return Whether or not the {@link Glass} was set.
     */
    public boolean setGlass(Glass glass) {
        if (glass == null) {
            return false;
        }

        this.glass = glass;

        return true;
    }

    /**
     * Returns the name of the customer who placed the {@link Order}.
     *
     * @return The name of the customer who placed the {@link Order}.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer who placed the {@link Order}.
     *
     * @param customerName The name to set.
     * @return Whether or not to the customer name was set.
     */
    public boolean setCustomerName(String customerName) {

        if (customerName == null || customerName.length() < 1) {
            return false;
        }

        this.customerName = customerName;
        return true;
    }

    /**
     * Returns the address of the customer who placed the {@link Order}.
     *
     * @return The address of the customer who placed the {@link Order}.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets the address of the customer who placed the {@link Order}.
     *
     * @param customerAddress The address to set.
     * @return Whether or not the address was set.
     */
    public boolean setCustomerAddress(String customerAddress) {
        if (customerAddress == null || customerAddress.length() < 1) {
            return false;
        }
        this.customerAddress = customerAddress;
        return true;
    }

    /**
     * Returns the zip-code of the customer who placed the {@link Order}.
     *
     * @return The zip-code of the customer who placed the {@link Order}.
     */
    public String getCustomerZip() {
        return customerZip;
    }

    /**
     * Sets the zip-code of the customer who placed the {@link Order}.
     *
     * @param customerZip The zip-code to set.
     * @return Whether or not the zip-code was set.
     */
    public boolean setCustomerZip(String customerZip) {
        if (customerZip == null || customerZip.length() < 1) {
            return false;
        }
        this.customerZip = customerZip;
        return true;
    }

    /**
     * Returns the city of the customer who placed the {@link Order}.
     *
     * @return The city of the customer who placed the {@link Order}.
     */
    public String getCustomerCity() {
        return customerCity;
    }

    /**
     * Sets the city of the customer who placed the {@link Order}.
     *
     * @param customerCity The city to set.
     * @return Whehter or not the city was set.
     */
    public boolean setCustomerCity(String customerCity) {
        if (customerCity == null || customerCity.length() < 1) {
            return false;
        }

        this.customerCity = customerCity;
        return true;
    }

    /**
     * Returns the email of the customer who placed the {@link Order}.
     *
     * @return The email of the customer who placed the {@link Order}.
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the email of the customer who placed the {@link Order}.
     *
     * @param customerEmail The email to set.
     * @return Whether or not the email was set.
     */
    public boolean setCustomerEmail(String customerEmail) {
        if (customerEmail == null || customerEmail.length() < 1) {
            return false;
        }
        this.customerEmail = customerEmail;
        return true;
    }

    /**
     * Returns the phone number of the customer who placed the {@link Order}.
     *
     * @return The phone number of the customer who placed the {@link Order}.
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Sets the phone number of the customer who placed the {@link Order}.
     *
     * @param customerPhone The phone number to set.
     * @return Whether or not the phone number was set.
     */
    public boolean setCustomerPhone(String customerPhone) {
        if (customerPhone == null || customerPhone.length() < 1) {
            return false;
        }
        this.customerPhone = customerPhone;
        return true;
    }
}
