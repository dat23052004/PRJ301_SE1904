/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddshop.controller.admin.management.user;

import ddshop.dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ddshop.model.Users;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "InsertUserServlet", urlPatterns = {"/InsertUserServlet"})
public class InsertUserServlet extends HttpServlet {

    private static final String MANAGE_USER_CONTROLLER = "ManageUserServlet";
    private static final String INSERT_USER_PAGE = "view/jsp/admin/admin_user_insert.jsp";

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
        String url = MANAGE_USER_CONTROLLER;
        try {
            UserDAO dao = new UserDAO();
            String avatar = request.getParameter("avatar");
            String fullName = request.getParameter("fullname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");

            String image = "";
            String firstName = fullName;
            String lastName = "";
            int roleId = 2;
            if (dao.CheckExistUsername(username)) {
                url = INSERT_USER_PAGE;
                request.setAttribute("error", "Tên tài khoản đã tồn tại!");
            } else {
                if (!avatar.equals("")) {
                    avatar = "view/assets/home/img/users/" + avatar;
                }
                if (fullName.split(" ").length > 0) {
                    String[] nameParts = fullName.split(" ");
                    firstName = nameParts[0];
                    lastName = String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length));
                }
                if ("admin".equals(role)) {
                    roleId = 1;
                }
                Users user = new Users(0, firstName, lastName, email, avatar, username, password, address, phone, roleId, true);
                dao.registerUser(user);
                request.setAttribute("mess", "Insert successfully!");
            }
        } catch (Exception ex) {
            log("InserUserServlet error:" + ex.getMessage());
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
