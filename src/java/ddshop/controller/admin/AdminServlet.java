/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ddshop.controller.admin;

import ddshop.dao.OrderDAO;
import ddshop.dao.OrderItemDAO;
import ddshop.dao.ProductDAO;
import ddshop.dao.UserDAO;
import ddshop.model.Orders;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class AdminServlet extends HttpServlet {

    private static final String ADMIN = "view/jsp/admin/admin_home.jsp";
    private final static String ORDER_DETAIL_PAGE = "view/jsp/admin/admin_order_detail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO pDao = new ProductDAO();
        OrderItemDAO oiDapo = new OrderItemDAO();
        OrderDAO oDao = new OrderDAO();
        UserDAO uDao = new UserDAO();
        String url = ADMIN;
        try {
            int totalProducts = pDao.getTotalProduct();
            int totalUsers = uDao.getTotalUsers();
            int totalOrders = oDao.getTotalOrders();
            int numberProductsLowQuantity = pDao.getProductsLowQuantiry();
            double totalSale = oDao.getTotalSale();
            double totalSaleTD = oDao.getTotalSaleToday();

            List<Orders> lastRecentOrders = oDao.getRecentOrders();

            request.setAttribute("TOTALSALE", totalSale);
            request.setAttribute("TOTALSALETODAY", totalSaleTD);
            request.setAttribute("TOTALPRODUCTS", totalProducts);
            request.setAttribute("PRODUCTSLOW", numberProductsLowQuantity);
            request.setAttribute("TOTALUSERS", totalUsers);
            request.setAttribute("TOTALORDERS", totalOrders);
            request.setAttribute("LAST_RECENT_ORDERS", lastRecentOrders);
            request.setAttribute("CURRENTSERVLET", "Dashboard");
            String action = request.getParameter("showdetail");
        } catch (Exception e) {
            log("AdminServlet error:" + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
