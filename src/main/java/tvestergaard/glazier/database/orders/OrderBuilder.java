/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.database.orders;

import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.frames.FrameReference;
import tvestergaard.glazier.database.glass.GlassReference;

/**
 *
 * @author Skole
 */
public class OrderBuilder {

    private int widthMM = 0;
    private int heightMM = 0;
    private FrameReference frame;
    private GlassReference glass;
    private String customerName;
    private String customerAddress;
    private String customerZip;
    private String customerCity;
    private String customerEmail;
    private String customerPhone;

    public int getWidthMM() {
        return widthMM;
    }

    public boolean setWidthMM(int widthMM) {
        if (widthMM < 0) {
            return false;
        }

        this.widthMM = widthMM;
        return true;
    }

    public int getHeightMM() {
        return heightMM;
    }

    public boolean setHeightMM(int heightMM) {
        if (heightMM < 0) {
            return false;
        }
        this.heightMM = heightMM;
        return true;
    }

    public FrameReference getFrame() {
        return frame;
    }

    public boolean setFrame(FrameReference frame) {
        if (frame == null) {
            return false;
        }

        this.frame = frame;
        return true;
    }

    public GlassReference getGlass() {
        return glass;
    }

    public boolean setGlass(GlassReference glass) {
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
