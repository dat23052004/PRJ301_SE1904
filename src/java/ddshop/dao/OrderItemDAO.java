/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.CartItem;
import ddshop.model.Orders;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author MY PC
 */
public class OrderItemDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;
    private static final String CREATE_NEW_ORDER_ITEM = "insert into OrderItem(quantity,price,product_id,order_id) values(?,?,?,?)";
    private static final String GET_ORDER_ITEM_BY_ORDER_ID = "SELECT order_id, product_id, SUM(quantity) AS quantity, price FROM OrderItem WHERE order_id = ? GROUP BY order_id, product_id, price";

    public boolean createNewOrderDetail(CartItem cart, Orders orders) {
        try {
            stm = connection.prepareStatement(CREATE_NEW_ORDER_ITEM);
            stm.setInt(1, cart.getQuantity());
            stm.setDouble(2, orders.getTotalPrice());
            stm.setInt(3, cart.getProduct().getId());
            stm.setInt(2, orders.getOrderId());
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("GetOrderItem: " + e.getMessage());
        }
        return false;
    }

}
