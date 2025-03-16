/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dal;

import ddshop.dao.ProductDAO;
import ddshop.model.CartItem;
import ddshop.model.Products;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class CartUtil {

    private static HashMap<Integer, CartItem> listItemsInCart = new HashMap<>();

    public HashMap<Integer, CartItem> createCart(CartItem cartItem) {
        listItemsInCart = new HashMap<>();
        listItemsInCart.put(cartItem.getProduct().getId(), cartItem);
        return listItemsInCart;
    }

    public HashMap<Integer, CartItem> addItemToCart(CartItem cartItem) {
        if (checkItemExist(cartItem.getProduct())) {
            CartItem itemExist = listItemsInCart.get(cartItem.getProduct().getId());
            itemExist.setQuantity(itemExist.getQuantity() + cartItem.getQuantity());
            listItemsInCart.put(itemExist.getProduct().getId(), itemExist);
        } else {
            listItemsInCart.put(cartItem.getProduct().getId(), cartItem);
        }
        return listItemsInCart;
    }

    public boolean checkItemExist(Products product) {
        for (Integer id : listItemsInCart.keySet()) {
            if (product.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public HashMap<Integer, CartItem> updateItemToCart(CartItem item) {
        if (checkItemExist(item.getProduct())) {
            CartItem itemExist = listItemsInCart.get(item.getProduct().getId());
            itemExist.setQuantity(item.getQuantity());
            listItemsInCart.put(itemExist.getProduct().getId(), itemExist);
        } else {
            listItemsInCart.put(item.getProduct().getId(), item);
        }
        return listItemsInCart;
    }

    public HashMap<Integer, CartItem> removeItem(Products products) {
        listItemsInCart.remove(products.getId());
        return listItemsInCart;
    }

    public Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] arrCookies = request.getCookies();
        if (arrCookies != null) {
            for (Cookie cookie : arrCookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public void saveCartToCookie(HttpServletRequest request,
            HttpServletResponse response, String strItemsInCart) {
        String cookieName = "Cart";

        Cookie cookieCart = getCookieByName(request, cookieName);

        if (cookieCart != null) {
            cookieCart.setValue(strItemsInCart);
        } else {
            cookieCart = new Cookie(cookieName, strItemsInCart);
        }

        cookieCart.setMaxAge(60 * 60 * 24 * 30 * 3);
        response.addCookie(cookieCart);
    }

    public String convertToString() {
        List<CartItem> list = new ArrayList<>(listItemsInCart.values());
        return list.toString();
    }

    public List<CartItem> getCartFromCookie(Cookie cookieCart) {
        ProductDAO pDao = new ProductDAO();
        List<CartItem> listItemsCart = new ArrayList<>();
        String inputString = cookieCart.getValue();
        if (inputString.startsWith("[") && inputString.endsWith("]")) {
            inputString = inputString.substring(1, inputString.length() - 1);
        }

        String[] elements = inputString.split(",");

        List<Products> products = new ArrayList<>();
        for (int i = 0; i < elements.length; i += 2) {
            Products product = pDao.getProductById(Integer.parseInt(elements[i].trim()));
            products.add(product);
        }
        List<Integer> quantitys = new ArrayList<>();
        for (int i = 1; i < elements.length; i += 2) {
            quantitys.add(Integer.parseInt(elements[i].trim()));
        }

        for (int i = 0; i < products.size(); i++) {
            CartItem item = new CartItem(products.get(i), quantitys.get(i));
            listItemsCart.add(item);
        }

        for (CartItem cartItem : listItemsCart) {
            addItemToCart(cartItem);
        }
        return listItemsCart;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        CartUtil cartUtil = new CartUtil();
        ProductDAO pDao = new ProductDAO();
        Products product1 = pDao.getProductById(1);
        Products product2 = pDao.getProductById(2);
        CartItem item1 = new CartItem(product1, 2);
        CartItem item2 = new CartItem(product2, 2);

        HashMap<Integer, CartItem> carts = cartUtil.createCart(item1);
        carts = cartUtil.addItemToCart(item2);
        List<CartItem> list = new ArrayList<>(carts.values());
//        String string = cartUtil.convertToString();
//        System.out.println(string);
        System.out.println(list);
    }
}
