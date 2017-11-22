/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvestergaard.glazier.database.orders;

/**
 *
 * @author Thomas
 */
public class UnknownOrderReferenceException extends Exception {

    private final OrderReference unknownOrderReference;

    public UnknownOrderReferenceException(OrderReference unknownOrderReference) {
        this.unknownOrderReference = unknownOrderReference;
    }

    public OrderReference getUnknownOrderReference() {
        return unknownOrderReference;
    }

}
