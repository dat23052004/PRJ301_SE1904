/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ddshop.controller.web;

import ddshop.dao.CategoryDAO;
import ddshop.dao.ProductDAO;
import ddshop.dao.SupplierDAO;
import ddshop.dao.TypeDAO;
import ddshop.model.Categorys;
import ddshop.model.Products;
import ddshop.model.Suppliers;
import ddshop.model.Types;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

    private final String LOGIN = "Login";
    private final String SEARCH = "Search";
    private final String LOGOUT = "Logout";
    private final String REGISTER = "Register";
    private final String ADDTOWISHLIST = "AddToWishList";
    private final String WELCOME = "view/jsp/home/home.jsp";
    private final String LOGIN_CONTROLlER = "LoginServlet";
    private final String WISHLIST_CONTROLlER = "WishlistServlet";
    private final String REGISTER_CONTROLLER = "RegisterServlet";
    private final String SEARCH_CONTROLLER = "SearchServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = WELCOME;
        try {
            String btnString = request.getParameter("btnAction");
            HttpSession session = request.getSession();
            if (btnString == null) {
                getDataHomeLSP(request, response);
                request.setAttribute("CURRENTSERVLET", "Home");
            } else if (btnString.equals(LOGOUT)) {
                url = WELCOME;
                getDataHomeLSP(request, response);
                if (session.getAttribute("account") != null) {
                    session.removeAttribute("account");
                }
                request.setAttribute("CURRENTSERVLET", "Home");
            } else if (btnString.equals(LOGIN)) {
                url = LOGIN_CONTROLlER;
            } else if (btnString.equals(REGISTER)) {
                url = REGISTER_CONTROLLER;
            } else if (btnString.equals(SEARCH)) {
                url = SEARCH_CONTROLLER;
            } else if (btnString.equals(ADDTOWISHLIST)) {
                url = WISHLIST_CONTROLlER;
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private void getDataHomeLSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProductDAO productDAO = new ProductDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            SupplierDAO supplierDAO = new SupplierDAO();
            TypeDAO typeDAO = new TypeDAO();

//            List<Products> listProducts = productDAO.getData();
            List<Categorys> listCategories = categoryDAO.getData();
            List<Suppliers> listSuppliers = supplierDAO.getData();
            List<Products> listProductsNew = productDAO.getProductNew();
            List<Products> listProductsBestSeller = productDAO.getProductsBestSeller();
            List<Types> listTypes = typeDAO.getAllTypes();

            
//            request.setAttribute("LIST_PRODUCTS", listProducts);
            request.setAttribute("LIST_TYPES", listTypes);
            request.setAttribute("LIST_CATEGORIESS", listCategories);
            request.setAttribute("LIST_SUPPLIERS", listSuppliers);
            request.setAttribute("LIST_PRODUCTS_NEW", listProductsNew);
            request.setAttribute("LIST_PRODUCTS_SELLER", listProductsBestSeller);
       
        } catch (Exception e) {
            log("DispatchServlet error:" + e.getMessage());

        }

    }

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
