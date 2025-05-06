/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ddshop.controller.cart;

import ddshop.dal.WishlistUtil;
import ddshop.dao.ProductDAO;
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

/**
 *
 * @author MY PC
 */
@WebServlet(name="WishlistServlet", urlPatterns={"/WishlistServlet"})
public class WishlistServlet extends HttpServlet {
   
    
     private static final String DISPATCHSERVLET = "DispatchServlet";
    private static final String WISHLIST_PAGE = "view/jsp/home/wishlist.jsp";
    private static final String WISHLIST_AJAX = "view/jsp/ajax/wishlist_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = WISHLIST_AJAX;
        ProductDAO pDao = new ProductDAO();
        WishlistUtil wUtil = new WishlistUtil();
        List<Products>  wishlists = null;
        HashMap<Integer, Products> listItem = null;
        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            if(action == null) {
                url = WISHLIST_PAGE;
            }else {
                String product_id = request.getParameter("product_id");        
                
                Products product = pDao.getProductById(Integer.parseInt(product_id));
                if ("Add".equals(action)) {
                    wishlists = (List<Products>) session.getAttribute("WISHLIST");
                    if (wishlists == null) {
                        listItem = wUtil.createWishlist(product);
                    } else {
                        listItem = wUtil.addItemToWishlist(product);
                    }
                } else if ("Delete".equals(action)) {
                    url = WISHLIST_PAGE;
                    listItem = wUtil.removeItem(product);

                }
            }
            
            // Save to Cookie
            wishlists = new ArrayList<>(listItem.values());
            session.setAttribute("WISHLIST", wishlists);
            
            String strItemsWishlist = wUtil.convertToString();
            wUtil.saveWishlistToCookie(request, response, strItemsWishlist);
            
        } catch (Exception ex) {
            log("WishlistServlet error:" + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
