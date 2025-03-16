/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Payments;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class PaymentDAO extends DBContext {
    
    PreparedStatement stm;
    ResultSet rs;
    
    private static final String GET_PAYMENTNAME_BYID = "SELECT * FROM Payments WHERE paymentid = ?";
    private static final String GET_PAYMENTNAME_DATA = "SELECT * FROM Payments";
    
    public Payments getPaymentById(int paymentId) {
        Payments pm = null;
        try {
            stm = connection.prepareStatement(GET_PAYMENTNAME_BYID);
            stm.setInt(1, paymentId);
            rs = stm.executeQuery();
            while(rs.next()){
                String method = rs.getString("payment_method");
                pm = new Payments(paymentId, method);
            }
        } catch (Exception e) {
            System.out.println("GetPayments: " + e.getMessage());
        }
        return pm;
    }
    
    public List<Payments> getData() {
        List<Payments> list = new ArrayList<>();
        
        try {
            stm = connection.prepareStatement(GET_PAYMENTNAME_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("paymentid");
                String method = rs.getString("payment_method");
                Payments payment = new Payments(id, method);
                list.add(payment);
            }
        } catch (Exception e) {
            System.out.println("GetPayments: " + e.getMessage());
        }
        return list;
    }
    
}
