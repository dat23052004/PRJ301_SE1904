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
public class Products {
    int id, stock, unitSold ;
    String name, description;
    String[] images, colors, sizes;
    Date releasedate;
    double discount,price, salePrice ;
    boolean status;
    Categorys category;
    Suppliers supplier;
    Types type;

    public Products() {
    }

    public Products(int id, int stock, int unitSold, String name, String description, String[] images, String[] colors, String[] sizes, Date releasedate, double discount, double price, boolean status, Categorys category, Suppliers supplier, Types type) {
        this.id = id;
        this.stock = stock;
        this.unitSold = unitSold;
        this.name = name;
        this.description = description;
        this.images = images;
        this.colors = colors;
        this.sizes = sizes;
        this.releasedate = releasedate;
        this.discount = discount;
        this.price = price;
        this.status = status;
        this.category = category;
        this.supplier = supplier;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getUnitSold() {
        return unitSold;
    }

    public void setUnitSold(int unitSold) {
        this.unitSold = unitSold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String[] getSizes() {
        return sizes;
    }

    public void setSizes(String[] sizes) {
        this.sizes = sizes;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Categorys getCategory() {
        return category;
    }

    public void setCategory(Categorys category) {
        this.category = category;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }
    
    public double getSalePrice() {
        double salePrice = price - Math.round(price * discount * 100) / 100.0;
        salePrice = Math.round(salePrice * 100.0) / 100.0;
        if (discount > 0) {
            return salePrice;
        } else {
            return price;
        }
    }

    
}
