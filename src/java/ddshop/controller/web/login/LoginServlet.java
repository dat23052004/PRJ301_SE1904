/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ddshop.controller.web.login;

import ddshop.dao.UserDAO;
import ddshop.model.Users;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String WELCOME = "DispatchServlet";
    private final String LOGIN = "view/jsp/home/login.jsp";
    private final String ADMIN_DASHBOARD = "AdminServlet";
    private final String REGISTER_CONTROLLER = "RegisterServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = WELCOME;
        try {
            if (request.getParameter("btnAction") != null) {
                Cookie arr[] = request.getCookies();
                if (arr != null) {
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].getName().equals("Name")) {
                            request.setAttribute("uName", arr[i].getValue());
                        }
                        if (arr[i].getName().equals("Pass")) {
                            request.setAttribute("uPass", arr[i].getValue());
                        }
                        if (arr[i].getName().equals("Mem")) {
                            request.setAttribute("reMem", arr[i].getValue());
                        }
                    }
                }
                url = LOGIN;
            }
        } catch (Exception ex) {
            log("LoginServlet error:" + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = WELCOME;
        try {
            String btnValue = request.getParameter("btnAction");
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            String remember = request.getParameter("remember");
            UserDAO udao = new UserDAO();
            Users user = udao.checkLogin(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", user);
                Cookie u = new Cookie("Name", username);
                Cookie p = new Cookie("Pass", password);
                Cookie r = new Cookie("Mem", remember);

                u.setMaxAge(60 * 60 * 24 * 30 * 3); //3months == 90 days
                if (remember != null) {
                    p.setMaxAge(60 * 60 * 24 * 30 * 3);
                    r.setMaxAge(60 * 60 * 24 * 30 * 3);
                } else {
                    p.setMaxAge(0);
                    r.setMaxAge(0);
                }

                response.addCookie(u);
                response.addCookie(p);
                response.addCookie(r);
//                if (user.getRole() == 1) {
//                    response.sendRedirect(ADMIN_DASHBOARD);
//                } else {
//                    response.sendRedirect(WELCOME);
//                }
            } else {
                String error = "Invalid username or password!";
                request.setAttribute("msg", error);
                url = LOGIN;
            }
        } catch (Exception ex) {
            log("LoginServlet error:" + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
