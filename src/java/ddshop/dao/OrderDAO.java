/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Orders;
import ddshop.model.Payments;
import ddshop.model.Users;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author MY PC
 */
public class OrderDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;
    
    private UserDAO uDao = new UserDAO();
    private PaymentDAO pDao = new PaymentDAO();

    private static final String GET_ORDER_BY_USERNAME = "SELECT * FROM Orders WHERE username = ?";

    public List<Orders> getOrdersByUsername(String usename) {
        List<Orders> list = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_ORDER_BY_USERNAME);
            stm.setString(1, usename);
            rs = stm.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Date orderDate = rs.getDate("orderdate");
                double totalPrice = rs.getDouble("totalprice");
                int paymentId = rs.getInt("paymentid");
                Payments payment = pDao.getPaymentById(paymentId);
                Users user = uDao.getUserByName(rs.getString("username"));
                boolean status = rs.getBoolean("status");
                Orders o = new Orders(orderId, orderDate, totalPrice, payment, user, status);
                list.add(o);
            }
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return list;
    }

}
