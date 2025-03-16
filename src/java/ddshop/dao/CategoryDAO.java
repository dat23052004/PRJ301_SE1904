/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Categorys;
import ddshop.model.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class CategoryDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "select * from Categories";
    private static final String GET_CATEGORYS_BY_ID = "select * from Categories where categoryid = ?";

    public List<Categorys> getData() {
        List<Categorys> data = new ArrayList<Categorys>();
        try {
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                TypeDAO tDAO = new TypeDAO();
                Types type = tDAO.getTypeById(rs.getInt(3));
                int categoryID = rs.getInt(1);
                String categoryName = rs.getString(2);
                Categorys category = new Categorys(categoryID, categoryName, type);
                data.add(category);
            }
        } catch (Exception e) {
            System.out.println("getTypes: " + e.getMessage());
        }
        return data;
    }

    Categorys getCategoryById(int id) {
    try {
            stm = connection.prepareStatement(GET_CATEGORYS_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                TypeDAO tDao = new TypeDAO();
                Types type = tDao.getTypeById(rs.getInt(3));
                Categorys category = new Categorys(id, name, type);               
                return category;
            }

        } catch (Exception e) {
            System.out.println("getTypes: " + e.getMessage());

        }
        return null; 
    }
    public static void main(String[] args) {
        List<Categorys> list = (new CategoryDAO()).getData();
        if(list == null || list.size()==0){
            System.out.println("hihi");
        }else{
            System.out.println(list.size()-1);
        }
        
    }
}
