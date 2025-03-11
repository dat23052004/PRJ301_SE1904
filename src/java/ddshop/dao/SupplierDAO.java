/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Suppliers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class SupplierDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;
     
    private static final String GET_SUPPLIER_BY_ID = "select * from Suppliers where supplierid = ? ";
    
    public List<Suppliers> getData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Suppliers getSupplierById(int id) {
        try {
            stm = connection.prepareStatement(GET_SUPPLIER_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                String image = rs.getString(3);
                Suppliers supplier = new Suppliers(id, name, image);
                return supplier;
            }
        } catch (Exception e) {
            System.out.println("getTypes: " + e.getMessage());

        }
        return null;
    }

}
