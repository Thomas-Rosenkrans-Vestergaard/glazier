package tvestergaard.glazier.database.orders;

import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

public final class Order extends OrderReference {

    private int widthMM;
    private int heightMM;
    private Frame frame;
    private Glass glass;
    private String customerName;
    private String customerAddress;
    private String customerZip;
    private String customerCity;
    private String customerEmail;
    private String customerPhone;

    public Order(int id, int widthMM, int heightMM, Frame frame, Glass glass, String customerName, String customerAddress, String customerZip, String customerCity, String customerEmail, String customerPhone) {
        super(id);
        this.widthMM = widthMM;
        this.heightMM = heightMM;
        this.frame = frame;
        this.glass = glass;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZip = customerZip;
        this.customerCity = customerCity;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    public int getWidthMM() {
        return widthMM;
    }

    public void setWidthMM(int widthMM) {
        this.widthMM = widthMM;
    }

    public int getHeightMM() {
        return heightMM;
    }

    public void setHeightMM(int heightMM) {
        this.heightMM = heightMM;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Glass getGlass() {
        return glass;
    }

    public void setGlass(Glass glass) {
        this.glass = glass;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerZip() {
        return customerZip;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public void setCustomerZip(String customerZip) {
        this.customerZip = customerZip;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

}
