/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.CartItem;
import ddshop.model.Categorys;
import ddshop.model.Products;
import ddshop.model.Suppliers;
import ddshop.model.Types;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "SELECT * FROM Products WHERE status = 1";
    private static final String GET_PRODUCTS_NEW_BY_YEAR = "SELECT * from Products WHERE year(releasedate) = 2024 AND status = 1";
    private static final String GET_PRODUCTS_BEST_SELLER = "select top(5) * from Products where status=1 order by unitSold desc";
    private static final String GET_PRODUCTS_BY_SUPPLIER_ID = "SELECT * FROM Products WHERE supplierid = ? AND status = 1";
    private static final String GET_PRODUCTS_BY_ID = "SELECT * FROM Products WHERE id = ? AND status = 1";
    private static final String UPDATE_QUANTITY_PRODUCT = "UPDATE Products SET [stock] = ? WHERE id = ?";
    private static final String GET_STOCK = "SELECT stock AS Total FROM Products WHERE id = ?";
    private static final String GET_TOTAL_PRODUCTS = "SELECT COUNT(*) AS Total FROM Products WHERE status = 1";
    private static final String GET_PRODUCTS_LOW_QUANTITY = "SELECT COUNT(*) AS Total from Products WHERE Stock < 10 AND status = 1";
    private static final String INSERT_PRODUCT = "INSERT INTO Products VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DELETE_PRODUCT = "UPDATE Products SET status = 0 WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE Products SET productname = ?, [description] = ?, stock = ?, "
            + "[images] = ?, [colors] = ?, size = ?, releasedate = ?, discount = ?, "
            + "price = ?, categoryid = ?, supplierid = ?, typeid = ? WHERE id = ?";

    public List<Products> getData() {
        List<Products> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_DATA);
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
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
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
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<Products> getProductsBestSeller() {
        List<Products> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BEST_SELLER);
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
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;

    }

    public List<Products> searchByCheckBox(List<Products> pList, int[] mult_id_filter) {
        List<Products> list = new ArrayList<>();

        if (mult_id_filter[0] == 0) {
            return pList;
        }

        for (Products product : pList) {
            for (int i = 0; i < mult_id_filter.length; i++) {
                if (product.getCategory().getId() == mult_id_filter[i]) {
                    list.add(product);
                }
            }
        }
        return list;
    }

    public List<Products> getProductSupplierId(int id_filter) {
        List<Products> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BY_SUPPLIER_ID);
            stm.setInt(1, id_filter);
            rs = stm.executeQuery();
            while (rs.next()) {
                CategoryDAO cDao = new CategoryDAO();
                SupplierDAO sDao = new SupplierDAO();
                TypeDAO tDao = new TypeDAO();
                Types type = tDao.getTypeById(rs.getInt("typeid"));
                Categorys category = cDao.getCategoryById(rs.getInt("categoryid"));
                Suppliers supplier = sDao.getSupplierById(id_filter);
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
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<Products> sortProduct(List<Products> pList, String valueSort) {
        List<Products> result = new ArrayList<>(pList);
        if (valueSort.equals("1")) {
            Collections.sort(result, (Products s1, Products s2) -> {
                return Double.compare(s1.getSalePrice(), s2.getSalePrice());
            });
        } else if (valueSort.equals("2")) {
            Collections.sort(result, (Products s1, Products s2) -> {
                return -(Double.compare(s1.getSalePrice(), s2.getSalePrice()));
            });
        } else if (valueSort.equals("3")) {
            Collections.sort(result, (Products s1, Products s2) -> {
                return s1.getName().compareTo(s2.getName());
            });
        }
        return result;
    }

    public List<Products> searchByPrice(List<Products> pList, double priceFrom, double priceTo) {
        List<Products> list = new ArrayList<>();
        for (Products product : pList) {
            if (priceFrom != 0) {
                if (priceTo != 0) {
                    if (product.getSalePrice() >= priceFrom && product.getSalePrice() <= priceTo) {
                        list.add(product);
                    }
                } else if (product.getSalePrice() >= priceFrom) {
                    list.add(product);
                }
            }
        }
        return list;
    }

    public List<Products> searchByColor(List<Products> pList, String color) {
        List<Products> list = new ArrayList<>();
        for (int i = 0; i < pList.size(); i++) {
            for (int j = 0; j < pList.get(i).getColors().length; j++) {
                if (pList.get(i).getColors()[j].contains(color)) {
                    list.add(pList.get(i));
                }
            }
        }
        return list;
    }

    public List<Products> searchByDiscount(List<Products> pList, double discount) {
        List<Products> list = new ArrayList<>();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getDiscount() > discount) {
                list.add(pList.get(i));
            }
        }
        return list;
    }

    public List<Products> getListByPage(List<Products> pList, int start, int end) {
        List<Products> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(pList.get(i));
        }
        return arr;
    }

    public static void main(String[] args) {
        List<Products> list = (new ProductDAO()).getData();
        System.out.println(list.size());
    }

    public Products getProductById(int product_id) {
        Products product = null;
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BY_ID);
            stm.setInt(1, product_id);
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
                int stock = rs.getInt("stock");
                int unitSold = rs.getInt("unitSold");
                double discount = rs.getDouble("discount");
                double price = rs.getDouble("price");
                boolean status = rs.getBoolean("status");
                Date date = rs.getDate("releasedate");
                String[] size = rs.getString("size").split(",");
                String[] color = rs.getString("colors").split(",");
                String[] image = rs.getString("images").split(" ");

                product = new Products(product_id, stock, unitSold, name, description, image, color, size, date, discount, price, status, category, supplier, type);
            }
        } catch (Exception e) {
        }
        return product;
    }

    public List<Products> getProductByCategoryId(List<Products> data, int id) {
        List<Products> listRs = new ArrayList<>();
        if (id == 0) {
            return data;
        }
        for (Products p : data) {
            if (p.getCategory().getId() == id) {
                listRs.add(p);
            }
        }
        return listRs;
    }

    public int getStock(int id) {
        int result = 0;
        try {
            stm = connection.prepareStatement(GET_STOCK);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return result;
    }

    public void updateQuantityProduct(CartItem cart) {
        try {
            stm = connection.prepareStatement(UPDATE_QUANTITY_PRODUCT);
            stm.setInt(1, (cart.getProduct().getStock() - cart.getQuantity()));
            stm.setInt(2, cart.getProduct().getId());
            stm.executeQuery();
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }

    }

    public int getTotalProduct() {
        int result = 0;
        try {
            stm = connection.prepareStatement(GET_TOTAL_PRODUCTS);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return result;
    }

    public int getProductsLowQuantiry() {
        int result = 0;
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_LOW_QUANTITY);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return result;
    }

    public void deleteProduct(int pid) {
        try {
            stm = connection.prepareStatement(DELETE_PRODUCT);
            stm.setInt(1, pid);
            stm.executeUpdate();
        } catch (Exception e) {

        }
    }

    public void editProduct(int pid, String pname, String pdescription, int pstock, String image, String pcolors, String psizes, String pdate, double pdiscount, double pprice, int pcategory, int psupplier, int ptype) {
        try {
            stm = connection.prepareStatement(UPDATE_PRODUCT);
            stm.setString(1, pname);
            stm.setString(2, pdescription);
            stm.setInt(3, pstock);
            stm.setString(4, image);
            stm.setString(5, pcolors);
            stm.setString(6, psizes);
            stm.setDate(7, Date.valueOf(pdate)); // Chuyển String thành Date
            stm.setDouble(8, pdiscount);
            stm.setDouble(9, pprice);
            stm.setInt(10, pcategory);
            stm.setInt(11, psupplier);
            stm.setInt(12, ptype);
            stm.setInt(13, pid);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
    }

    public void insertProduct(String name, int cId, int sId, int tId, double price, double discount, String size, String color, int stock, String date, String image, String description) {
        try {
            stm = connection.prepareStatement(INSERT_PRODUCT);
            stm.setString(1, name);
            stm.setInt(2, sId);
            stm.setInt(3, cId);
            stm.setString(4, size);
            stm.setInt(5, stock);
            stm.setString(6, description);
            stm.setString(7, image);
            stm.setString(8, color);
            stm.setString(9, date);
            stm.setDouble(10, discount);
            stm.setInt(11, 0);
            stm.setDouble(12, price);
            stm.setInt(13, 1);
            stm.setInt(14, tId);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
    }

}
