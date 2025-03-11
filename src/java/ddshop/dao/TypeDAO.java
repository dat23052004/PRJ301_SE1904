/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class TypeDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;
    private static final String GET_TYPE_BY_ID = "select *from Types where id = ?";
    private static final String GET_ALL_TYPE = "select *from Types";

    public Types getTypeById(int id) {
        try {
            stm = connection.prepareStatement(GET_TYPE_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                Types type = new Types(id, name);
                return type;
            }

        } catch (Exception e) {
            System.out.println("getTypes: " + e.getMessage());

        }
        return null;
    }

    public List<Types> getAllTypes() {
        List<Types> data = new ArrayList<Types>();
        try {
            stm = connection.prepareStatement(GET_ALL_TYPE);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Types type = new Types(id, name);
                data.add(type);
            }
        } catch (Exception e) {
            System.out.println("getTypes: " + e.getMessage());
        }
        return data;
    }
}
