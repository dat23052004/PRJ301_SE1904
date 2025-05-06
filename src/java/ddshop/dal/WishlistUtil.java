/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dal;

import ddshop.dao.ProductDAO;
import ddshop.model.Products;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class WishlistUtil {
    private static HashMap<Integer, Products> listItemsInWishlist = new HashMap<>();

    public HashMap<Integer, Products> createWishlist(Products item) {
        listItemsInWishlist = new HashMap<>();
        listItemsInWishlist.put(item.getId(), item);
        return listItemsInWishlist;
    }

    public HashMap<Integer, Products> addItemToWishlist(Products item) {
        if (!checkItemExist(item)) {
            listItemsInWishlist.put(item.getId(), item);
        }
        return listItemsInWishlist;
    }

    public boolean checkItemExist(Products product) {
        for (Integer id : listItemsInWishlist.keySet()) {
            if (product.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public HashMap<Integer, Products> removeItem(Products product) {
        if (listItemsInWishlist.containsKey(product.getId())) {
            listItemsInWishlist.remove(product.getId());
        }
        return listItemsInWishlist;
    }

    // Xử lý với Cookie
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

    public void saveWishlistToCookie(HttpServletRequest request,
            HttpServletResponse response, String strItemsInWishList) {
        String cookieName = "Wishlist";

        Cookie cookieCart = getCookieByName(request, cookieName);

        if (cookieCart != null) {

            cookieCart.setValue(strItemsInWishList);

        } else {

            cookieCart = new Cookie(cookieName, strItemsInWishList);

        }

        cookieCart.setMaxAge(60 * 60 * 24 * 30 * 3);

        response.addCookie(cookieCart);

    }

    public String convertToString() {
        List<Products> list = new ArrayList<>(listItemsInWishlist.values());
        String result = "";
        for (Products productDTO : list) {
            result += productDTO.getId() + ",";
        }
        return result;

    }

    public List<Products> getWishlistFromCookie(Cookie cookieWishlist) {
        ProductDAO pDao = new ProductDAO();
        List<Products> listItemsCart = new ArrayList<>();
        String inputString = cookieWishlist.getValue();
        if (inputString.endsWith(",")) {
            inputString = inputString.substring(0, inputString.length() - 1);
        }

        if (inputString.length() > 0) {
            // Chia chuỗi thành các phần tử
            String[] elements = inputString.split(",");

            for (int i = 0; i < elements.length; i++) {
                Products product = pDao.getProductById(Integer.parseInt(elements[i].trim()));
                listItemsCart.add(product);
            }
        }
        // add to util
        for (Products productDTO : listItemsCart) {
            addItemToWishlist(productDTO);
        }

        return listItemsCart;
    }
}
