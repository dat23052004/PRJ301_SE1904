/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddshop.controller.admin.management.product;

import ddshop.dao.CategoryDAO;
import ddshop.dao.ProductDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ddshop.model.Categorys;
import ddshop.model.Products;

/**
 *
 * @author HuuThanh
 */
@WebServlet(name = "ManageProductServlet", urlPatterns = {"/ManageProductServlet"})
public class ManageProductServlet extends HttpServlet {

    private final String PRODUCT_PAGE = "view/jsp/admin/admin_products.jsp";
    private final String INSERT_PRODUCT_PAGE = "view/jsp/admin/admin_products_insert.jsp";
    private final String INSERT = "Insert";
    private final String DELETE_PRODUCT_CONTROLLER = "DeleteProductServlet";
    private final String INSERT_PRODUCT_CONTROLLER = "InsertProductServlet";
    private final String Delete = "Delete";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = PRODUCT_PAGE;
        try {
            String action = request.getParameter("action");
            ProductDAO pDao = new ProductDAO();
            CategoryDAO cDao = new CategoryDAO();

            List<Products> listProducts = pDao.getData();
            List<Categorys> listCategories = cDao.getData();
            if (action == null) {
                request.setAttribute("LIST_PRODUCTS", listProducts);
                request.setAttribute("LIST_CATEGORIES", listCategories);
                request.setAttribute("CURRENTSERVLET", "Product");
            } else if (action.equals(INSERT)) {
                url = INSERT_PRODUCT_CONTROLLER;
            } else if (action.equals("update")) {
                request.setAttribute("LIST_PRODUCTS", listProducts);
                request.setAttribute("LIST_CATEGORIES", listCategories);
                request.setAttribute("CURRENTSERVLET", "User");
            } 

        } catch (Exception ex) {
            log("ManageProductServlet error:" + ex.getMessage());
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
