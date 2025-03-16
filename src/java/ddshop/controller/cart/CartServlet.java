/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ddshop.controller.cart;

import ddshop.dal.CartUtil;
import ddshop.dao.ProductDAO;
import ddshop.model.CartItem;
import ddshop.model.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {

    private static final String CART_PAGE = "view/jsp/home/cart.jsp";
    private static final String CART_AJAX = "view/jsp/ajax/cart_ajax.jsp";
    private static final String CART_PAGE_AJAX = "view/jsp/ajax/cart_page_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CART_AJAX;
        ProductDAO pDao = new ProductDAO();
        CartUtil cartUtil = new CartUtil();
        List<CartItem> carts = null;
        HashMap<Integer, CartItem> listItem = null;

        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            if (action == null) {
                url = CART_PAGE;
            } else {
                String product_id = request.getParameter("product_id");
                Products product = pDao.getProductById(Integer.parseInt(product_id));
                if ("Add".equals(action)) {
                    String quantity = request.getParameter("quantity");
                    CartItem item = new CartItem(product, Integer.parseInt(quantity));
                    carts = (List<CartItem>) session.getAttribute("CART");
                    if (carts == null) {
                        listItem = cartUtil.createCart(item);
                    } else {
                        listItem = cartUtil.addItemToCart(item);
                    }
                } else if ("Delete".equals(action)) {
                    String curPage = request.getParameter("curPage");
                    if ("cart.jsp".equals(curPage)) {
                        url = CART_PAGE_AJAX;
                    } else if ("header.jsp".equals(curPage)) {
                        url = CART_AJAX;
                    }

                    listItem = cartUtil.removeItem(product);
                } else if ("Update".equals(action)) {
                    url = CART_PAGE_AJAX;
                    String quantity = request.getParameter("quantity");
                    CartItem item = new CartItem(product, Integer.parseInt(quantity));
                    listItem = cartUtil.updateItemToCart(item);
                }
            }
            carts = new ArrayList<>(listItem.values());
            session.setAttribute("CART", carts);
            // Save to cookie
            String strCarts = cartUtil.convertToString();
            cartUtil.saveCartToCookie(request, response, strCarts);
        } catch (Exception e) {
            log("CartServlet error:" + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
