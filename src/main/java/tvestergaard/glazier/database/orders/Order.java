package tvestergaard.glazier.database.orders;

import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

public final class Order extends OrderReference {

    private int widthMillimeters;
    private int heightMillimeters;
    private Frame frame;
    private Glass glass;
    private String customerName;
    private String customerAddress;
    private String customerZip;
    private String customerCity;
    private String customerEmail;
    private String customerPhone;

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

    public int getWidthMillimeters() {
        return widthMillimeters;
    }

    public boolean setWidthMillimeters(int widthMillimeters) {
        if (widthMillimeters < 0) {
            return false;
        }

        this.widthMillimeters = widthMillimeters;
        return true;
    }

    public int getHeightMillimeters() {
        return heightMillimeters;
    }

    public boolean setHeightMillimeters(int heightMillimeters) {
        if (heightMillimeters < 0) {
            return false;
        }
        this.heightMillimeters = heightMillimeters;
        return true;
    }

    public Frame getFrame() {
        return frame;
    }

    public boolean setFrame(Frame frame) {
        if (frame == null) {
            return false;
        }

        this.frame = frame;
        return true;
    }

    public Glass getGlass() {
        return glass;
    }

    public boolean setGlass(Glass glass) {
        if (glass == null) {
            return false;
        }

        this.glass = glass;

        return true;
    }

    public String getCustomerName() {
        return customerName;
    }

    public boolean setCustomerName(String customerName) {

        if (customerName == null || customerName.length() < 1) {
            return false;
        }

        this.customerName = customerName;
        return true;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public boolean setCustomerAddress(String customerAddress) {
        if (customerAddress == null || customerAddress.length() < 1) {
            return false;
        }
        this.customerAddress = customerAddress;
        return true;
    }

    public String getCustomerZip() {
        return customerZip;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public boolean setCustomerCity(String customerCity) {
        if (customerCity == null || customerCity.length() < 1) {
            return false;
        }

        this.customerCity = customerCity;
        return true;
    }

    public boolean setCustomerZip(String customerZip) {
        if (customerZip == null || customerZip.length() < 1) {
            return false;
        }
        this.customerZip = customerZip;
        return true;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public boolean setCustomerEmail(String customerEmail) {
        if (customerEmail == null || customerEmail.length() < 1) {
            return false;
        }
        this.customerEmail = customerEmail;
        return true;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public boolean setCustomerPhone(String customerPhone) {
        if (customerPhone == null || customerPhone.length() < 1) {
            return false;
        }
        this.customerPhone = customerPhone;
        return true;
    }
}
