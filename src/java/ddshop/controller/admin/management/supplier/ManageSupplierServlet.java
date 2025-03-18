/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddshop.controller.admin.management.supplier;

import ddshop.dao.SupplierDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ddshop.model.Suppliers;

/**
 *
 * @author HuuThanh
 */
@WebServlet(name = "ManageSupplierServlet", urlPatterns = {"/ManageSupplierServlet"})
public class ManageSupplierServlet extends HttpServlet {

    private final String MANAGESUPPLIERPAGE = "view/jsp/admin/admin_suppliers.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            SupplierDAO sDao = new SupplierDAO();
            List<Suppliers> list = sDao.getData();

            request.setAttribute("LISTSUPPLIERS", list);
            request.setAttribute("action", "MNGSUPPLIER");
            request.setAttribute("CURRENTSERVLET", "Supplier");
        } catch (Exception ex) {
            log("ManageSupplierServlet error:" + ex.getMessage());
        } finally {
            request.getRequestDispatcher(MANAGESUPPLIERPAGE).forward(request, response);
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
