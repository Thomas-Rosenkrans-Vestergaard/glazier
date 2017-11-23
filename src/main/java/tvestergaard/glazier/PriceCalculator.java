/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier;

import tvestergaard.glazier.database.frames.Frame;
import tvestergaard.glazier.database.glass.Glass;

/**
 *
 * @author Skole
 */
public class PriceCalculator {

    public double calculatePrice(Frame frame, Glass glass, int widthMM, int heightMM) {

        double price = 0.0;

        double framePricePerMM = (double)frame.getPricePerMeter() / 1000;
        price += framePricePerMM * (widthMM * 2 + heightMM * 2);

        int glassPricePerSM = glass.getPricePerSquareMeter();
        double productAreaM = (double)widthMM * (double)heightMM / 1000000;
        price += productAreaM * glassPricePerSM;

        return price;
    }
}
