/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ddshop.controller.admin.management.order;

import ddshop.dao.OrderDAO;
import ddshop.dao.OrderItemDAO;
import ddshop.model.OrderItems;
import ddshop.model.Orders;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "ManageOrderServlet", urlPatterns = {"/ManageOrderServlet"})
public class ManageOrderServlet extends HttpServlet {

    private final static String ORDER_PAGE = "view/jsp/admin/admin_order.jsp";
    private final static String ORDER_DETAIL_PAGE = "view/jsp/admin/admin_order_detail.jsp";
    private final static String CHANGE_STATUS = "changeStatus";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ORDER_PAGE;
        try {
            OrderDAO oDao = new OrderDAO();
            OrderItemDAO oIDao = new OrderItemDAO();
            List<Orders> listOrders = oDao.getAllOrders();
            System.out.println("listOrder:" + listOrders);
            String action = request.getParameter("action");
            
            if ("showdetail".equals(action)) {
                url = ORDER_DETAIL_PAGE;
                String bill_id = request.getParameter("bill_id");
                Orders order = oDao.getOrdersByID(Integer.parseInt(bill_id));
                int id = order.getOrderId();
                List<OrderItems> list = oIDao.getOrderItemByOrderId(id);
                request.setAttribute("LIST_PRODUCTS_IN_ORDER", list);

            } else if (CHANGE_STATUS.equals(action)) {
                oDao.UpdateStatus(request.getParameter("id"));
            }

            request.setAttribute("CURRENTSERVLET", "Order");
            request.setAttribute("LIST_ORDERS", listOrders);

        } catch (Exception e) {
            log("ManageOrderServlet error:" + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
