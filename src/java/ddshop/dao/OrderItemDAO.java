/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.CartItem;
import ddshop.model.OrderItems;
import ddshop.model.Orders;
import ddshop.model.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class OrderItemDAO extends DBContext {

    private ProductDAO pDao = new ProductDAO();

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
            stm.setInt(4, orders.getOrderId());
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("GetOrderItem: " + e.getMessage());
        }
        return false;
    }

    public List<OrderItems> getOrderItemByOrderId(int id) {
        List<OrderItems> list = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_ORDER_ITEM_BY_ORDER_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                int productID = rs.getInt("product_id");
                Products product = pDao.getProductById(productID);
                int orderID = rs.getInt("order_id");
                OrderItems order = new OrderItems(quantity, price, product, orderID);
                list.add(order);
            }
        } catch (Exception e) {
            System.out.println("GetOrderItem: " + e.getMessage());
        }
        return list;
    }

    public static void main(String[] args) {
        
    }

}
