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
public class UnknownOrderException extends UnknownOrderReferenceException {
    private Order unknownOrder;

    public UnknownOrderException(Order unknownOrder) {
        super(unknownOrder);
        this.unknownOrder = unknownOrder;
    }

    public Order getUnknownOrder() {
        return unknownOrder;
    }
}
