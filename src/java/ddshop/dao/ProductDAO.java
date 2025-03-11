/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Categorys;
import ddshop.model.Products;
import ddshop.model.Suppliers;
import ddshop.model.Types;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_PRODUCTS_NEW_BY_YEAR = "SELECT * from Products WHERE year(releasedate) = 2024 AND status = 1";

    public List<Products> getData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Products> getProductNew() throws SQLException {
        List<Products> data = new ArrayList<>();

        try {
            stm = connection.prepareStatement(GET_PRODUCTS_NEW_BY_YEAR);
            rs = stm.executeQuery();
            while (rs.next()) {
                CategoryDAO cDao = new CategoryDAO();
                SupplierDAO sDao = new SupplierDAO();
                TypeDAO tDao = new TypeDAO();
                Types type = tDao.getTypeById(rs.getInt("typeid"));
                Categorys category = cDao.getCategoryById(rs.getInt("categoryid"));
                Suppliers supplier = sDao.getSupplierById(rs.getInt("supplierid"));
                String name = rs.getString("productname");
                String description = rs.getString("description");
                int id = rs.getInt("id");
                int stock = rs.getInt("stock");
                int unitSold = rs.getInt("unitSold");
                double discount = rs.getDouble("discount");
                double price = rs.getDouble("price");
                boolean status = rs.getBoolean("status");
                Date date = rs.getDate("releasedate");
                String[] size = rs.getString("size").split(",");
                String[] color = rs.getString("colors").split(",");
                String[] image = rs.getString("images").split(" ");

                Products product = new Products(id, stock, unitSold, name, description, image, color, size, date, discount, price, status, category, supplier, type);
                data.add(product);
            }
        } catch (Exception e) {

        }
        return data;
    }

    public List<Products> getProductsBestSeller() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
