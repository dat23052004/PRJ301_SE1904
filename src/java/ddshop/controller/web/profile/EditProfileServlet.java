/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ddshop.controller.web.profile;

import ddshop.dao.UserDAO;
import ddshop.model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author MY PC
 */
@WebServlet(name="EditProfileServlet", urlPatterns={"/EditProfileServlet"})
public class EditProfileServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String firstName = request.getParameter("first-name");
            String lastName = request.getParameter("last-name");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String avatar = request.getParameter("avatar");
            String role_raw = request.getParameter("role");
            UserDAO uDao = new UserDAO();

            int roleId = (role_raw.equals("Admin") ? 1 : 2);
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("account");

            uDao.updateUser(firstName, lastName, email, address, phone, user.getUsername(), avatar, roleId);
            
            // refresh lại session user vì mới update
            user = uDao.checkLogin(user.getUsername(), user.getPassword());
            session.setAttribute("account", user);
            
            request.setAttribute("STATUS", "Update successfully!!!");
        } catch (Exception ex) {
            log("EditProfileServlet error:" + ex.getMessage());
        } finally {
            request.getRequestDispatcher("view/jsp/home/my-account.jsp").forward(request, response);
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    
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
