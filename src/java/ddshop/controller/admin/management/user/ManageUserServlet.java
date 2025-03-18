/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddshop.controller.admin.management.user;

import ddshop.dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ddshop.model.Users;
import java.util.ArrayList;

/**
 *
 * @author HuuThanh
 */
@WebServlet(name = "ManageUserServlet", urlPatterns = {"/ManageUserServlet"})
public class ManageUserServlet extends HttpServlet {

    private final String MANAGE_USER_PAGE = "view/jsp/admin/admin_users.jsp";
    private final String INSERT_USER_PAGE = "view/jsp/admin/admin_user_insert.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = MANAGE_USER_PAGE;
        try {
            String action = request.getParameter("action");
            UserDAO dao = new UserDAO();
            if (action == null) {
                List<Users> list = dao.getData();

                request.setAttribute("LISTUSERS", list);
                request.setAttribute("CURRENTSERVLET", "User");
                url = MANAGE_USER_PAGE;
            } else if (action.equals("Insert")) {
                url = INSERT_USER_PAGE;
            } else if (action.equals("Update")) {
                List<Users> list = dao.getData();
                request.setAttribute("CURRENTSERVLET", "User");
                request.setAttribute("LISTUSERS", list);
                url = MANAGE_USER_PAGE;
            }
        } catch (Exception ex) {
            log("ManageUserServlet error:" + ex.getMessage());
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
