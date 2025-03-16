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
import java.util.Collections;
import java.util.List;

public class ProductDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "SELECT * FROM Products WHERE status = 1";
    private static final String GET_PRODUCTS_NEW_BY_YEAR = "SELECT * from Products WHERE year(releasedate) = 2024 AND status = 1";
    private static final String GET_PRODUCTS_BEST_SELLER = "select top(5) * from Products where status=1 order by unitSold desc";
//    private static final String GET_PRODUCT_BY_COLOR = "select * From Products where colors like ?";
    private static final String GET_PRODUCTS_BY_SUPPLIER_ID = "SELECT * FROM Products WHERE supplierid = ? AND status = 1";
    private static final String GET_PRODUCTS_BY_ID = "SELECT * FROM Products WHERE id = ? AND status = 1";

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

    

}

//    public Products getProductByColor(String color) {
//        try {
//            stm = connection.prepareStatement(GET_PRODUCT_BY_COLOR);
//            stm.setString(9, "%" +color+"%");
//            rs = stm.executeQuery();
//            while (rs.next()) {
//                CategoryDAO cDao = new CategoryDAO();
//                SupplierDAO sDao = new SupplierDAO();
//                TypeDAO tDao = new TypeDAO();
//                Types type = tDao.getTypeById(rs.getInt("typeid"));
//                Categorys category = cDao.getCategoryById(rs.getInt("categoryid"));
//                Suppliers supplier = sDao.getSupplierById(rs.getInt("supplierid"));
//                String name = rs.getString("productname");
//                String description = rs.getString("description");
//                int id = rs.getInt("id");
//                int stock = rs.getInt("stock");
//                int unitSold = rs.getInt("unitSold");
//                double discount = rs.getDouble("discount");
//                double price = rs.getDouble("price");
//                boolean status = rs.getBoolean("status");
//                Date date = rs.getDate("releasedate");
//                String[] size = rs.getString("size").split(",");
//                String[] color1 = rs.getString("colors").split(",");
//                String[] image = rs.getString("images").split(" ");
//
//                Products product = new Products(id, stock, unitSold, name, description, image, color1, size, date, discount, price, status, category, supplier, type);
//                return product;
//            }
//        } catch (Exception e) {
//            System.out.println("getProduct: " + e.getMessage());
//
//        }
//        return null;
//    }
