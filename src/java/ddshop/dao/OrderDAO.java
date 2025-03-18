/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Orders;
import ddshop.model.Payments;
import ddshop.model.Users;
import java.sql.Connection;
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
    private static final String CREATE_ORDER = "insert into Orders(orderdate,totalprice,paymentid,username,status) values(?,?,?,?,0)";
    private static final String GET_TOTAL_ORDERS = "select count(*) as Total from Orders";
    private static final String GET_TOTAL_SALE = "SELECT SUM(totalprice) AS TotalSale from [Orders]";
    private static final String GET_TOTAL_SALE_DAY = "SELECT sum(totalprice) AS TotalSale FROM [Orders]  WHERE cast(orderdate as Date) = cast(getdate() as Date)";
    private static final String GET_RECENT_ORDERS = "SELECT Top 10 * FROM Orders ORDER BY orderdate DESC";
    private static final String GET_ALL_ORDERS = "SELECT * FROM [Orders] ORDER BY orderdate DESC";
    private static final String GET_ORDERS_BYID = "SELECT * FROM [Orders] WHERE order_id = ?";
    private static final String UPDATE_STATUS = "UPDATE [Orders] SET status = 1 WHERE order_id = ?";
    private static final String GET_LATEST_ORDER = "SELECT TOP 1 * FROM Orders ORDER BY order_id DESC";
    private static final String GET_TOTAL_MONEY_YEAR = "SELECT SUM(totalprice) AS TotalSale from [Orders] where year([orderdate]) = ? AND Status = 1";
    private static final String GET_TOTAL_MONEY_MONTH = "SELECT SUM(totalprice) AS TotalSale from [Orders] where month([orderdate]) = ? AND Status = 1";

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

    public boolean createNewOrder(String date, double total, Payments payment, Users user) {
        try {
            stm = connection.prepareStatement(CREATE_ORDER);
            stm.setString(1, date);
            stm.setDouble(2, total);
            stm.setInt(3, payment.getPaymentID());
            stm.setString(4, user.getUsername());
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return false;
    }

    public int getTotalOrders() {
        int result = 0;
        try {
            stm = connection.prepareStatement(GET_TOTAL_ORDERS);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return result;
    }

    public double getTotalSaleToday() {
        double result = 0;
        try {
            stm = connection.prepareStatement(GET_TOTAL_SALE_DAY);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getDouble("TotalSale");
            }
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return result;
    }

    public double getTotalSale() {
        double result = 0;
        try {
            stm = connection.prepareStatement(GET_TOTAL_SALE);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getDouble("TotalSale");
            }
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return result;
    }

    public List<Orders> getRecentOrders() {
        List<Orders> list = new ArrayList<>();

        try {
            stm = connection.prepareStatement(GET_RECENT_ORDERS);
            rs = stm.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                java.sql.Date orderDate = rs.getDate("orderdate");
                double totalPrice = rs.getDouble("totalprice");
                int paymentId = rs.getInt("paymentid");
                Payments payment = pDao.getPaymentById(paymentId);
                String userName = rs.getString("username");
                Users user = uDao.getUserByName(userName);
                boolean status = rs.getBoolean("status");
                Orders order = new Orders(orderId, orderDate, totalPrice, payment, user, status);
                list.add(order);
            }
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());

        }
        return list;
    }

    public List<Orders> getAllOrders() {
        List<Orders> list = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_ALL_ORDERS);
            rs = stm.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                java.sql.Date orderDate = rs.getDate("orderdate");
                double totalPrice = rs.getDouble("totalprice");
                int paymentId = rs.getInt("paymentid");
                Payments payment = pDao.getPaymentById(paymentId);
                String userName = rs.getString("username");
                Users user = uDao.getUserByName(userName);
                boolean status = rs.getBoolean("status");
                Orders order = new Orders(orderId, orderDate, totalPrice, payment, user, status);
                list.add(order);
            }
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());

        }
        return list;
    }

    public Orders getOrdersByID(int bill_id) {
        Orders order = null;
        try {
            stm = connection.prepareStatement(GET_ORDERS_BYID);
            stm.setInt(1, bill_id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Date orderDate = rs.getDate("orderdate");
                double totalPrice = rs.getDouble("totalprice");
                int paymentId = rs.getInt("paymentid");
                Payments payment = pDao.getPaymentById(paymentId);
                boolean status = rs.getBoolean("status");
                String userName = rs.getString("username");
                Users user = uDao.getUserByName(userName);
                order = new Orders(bill_id, orderDate, totalPrice, payment, user, status);
            }
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());

        }
        return order;
    }

    public void UpdateStatus(String parameter) {
        try {
            stm = connection.prepareStatement(UPDATE_STATUS);
            stm.setString(1, parameter);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Orders order = (new OrderDAO()).getOrdersByID(Integer.parseInt("4"));
        System.out.println(order.getOrderDate());
        System.out.println(order.getOrderId());
        System.out.println(order.getPaymentMethod());
        System.out.println(order.getTotalPrice());
        System.out.println(order.getUser());
    }

    public Orders getTheLatestOrder() {
        Orders order = null;
        try {

            stm = connection.prepareStatement(GET_LATEST_ORDER);
            rs = stm.executeQuery();
            if (rs.next()) {
                int orderId = rs.getInt("order_id");
                java.sql.Date orderDate = rs.getDate("orderdate");
                double totalPrice = rs.getDouble("totalprice");
                int paymentId = rs.getInt("paymentid");
                Payments payment = pDao.getPaymentById(paymentId);
                String userName = rs.getString("username");
                Users user = uDao.getUserByName(userName);
                boolean status = rs.getBoolean("status");
                order = new Orders(orderId, orderDate, totalPrice, payment, user, status);
            }

        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return order;
    }

    public double getTotalMoneyByYear(int year) {
        double result = 0;
        try {
            stm = connection.prepareStatement(GET_TOTAL_MONEY_YEAR);
            stm.setInt(1, year);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getDouble("TotalSale");
            }

        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return result;
    }

    public double getTotalMoneyByMonth(int month) {
        double result = 0;
        try {

            stm = connection.prepareStatement(GET_TOTAL_MONEY_MONTH);
            stm.setInt(1, month);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getDouble("TotalSale");
            }

        } catch (Exception e) {
            System.out.println("getOrder: " + e.getMessage());
        }
        return result;
    }

}
