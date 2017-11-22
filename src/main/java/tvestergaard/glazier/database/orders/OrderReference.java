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
public class OrderReference {
    private final int id;

    public OrderReference(int id) {
        this.id = id;
    }
    
    public static OrderReference of(int id){
        return new OrderReference(id);
    }

    public int getID() {
        return id;
    }
    
    
}
