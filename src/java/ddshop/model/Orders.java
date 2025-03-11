/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.model;

import java.util.Date;

/**
 *
 * @author MY PC
 */
public class Orders {
    int orderId;
    Date orderDate;
    double totalPrice;
    Payments paymentMethod;
    Users user;
    boolean status;

    public Orders() {
    }

    public Orders(int orderId, Date orderDate, double totalPrice, Payments paymentMethod, Users user, boolean status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.user = user;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Payments getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payments paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
