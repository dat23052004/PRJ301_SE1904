/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ddshop.controller.cart;

import ddshop.dal.CartUtil;
import ddshop.dao.OrderDAO;
import ddshop.dao.OrderItemDAO;
import ddshop.dao.PaymentDAO;
import ddshop.dao.ProductDAO;
import ddshop.model.CartItem;
import ddshop.model.Orders;
import ddshop.model.Payments;
import ddshop.model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

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
        PaymentDAO pmDAO = new PaymentDAO();
        ProductDAO pDao = new ProductDAO();
        OrderDAO oDao = new OrderDAO();
        OrderItemDAO oiDao = new OrderItemDAO();
        CartUtil cUtil = new CartUtil();

        Orders orders = null;
        double total = 0;
        int totalQuantitys = 0;
        String message = "";
        String check = "false";
        String emptyCart = "[]";
        try {
            HttpSession session = request.getSession();
            Cookie cookie = null;

            List<Payments> pmList = pmDAO.getData();

            // Check out
            String paymentId = request.getParameter("check_method");
            Users user = (Users) session.getAttribute("account");
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("CART");
            if (user != null && user.getRole() != 1 && paymentId != null) {
                Payments payment = pmDAO.getPaymentById(Integer.parseInt(paymentId));
                for (CartItem item : cartItems) {
                    // Check quanity of product in stock
                    if (pDao.getStock(item.getProduct().getId()) > 5 && item.getQuantity() < item.getProduct().getStock()) {
                        total += item.getProduct().getSalePrice() * item.getQuantity();
                        totalQuantitys += item.getQuantity();
                    }
                }
                LocalDateTime dateNow = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String date = dateNow.format(format);

                if (oDao.createNewOrder(date, total, payment, user)) {
                    message = "Order Success";

                    for (CartItem cart : cartItems) {
                        oiDao.createNewOrderDetail(cart, orders);
                        // Update product quantity
                        pDao.updateQuantityProduct(cart);
                    }
                    cartItems = null;
                    cookie = cUtil.getCookieByName(request, "Cart");
                    cUtil.saveCartToCookie(request, response, emptyCart);

                    session.setAttribute("CART", cartItems);
                    check = "true";
                }
            } else {
                if (user == null) {
                    message = "You need to log in to your account to checkout";
                } else if (user.getRole() == 1) {
                    message = "Admin cannot perform this task";
                }
            }

            request.setAttribute("PAYMENTS", pmList);
            request.setAttribute("MESSAGE", message);
            request.setAttribute("CHECK", check);
        } catch (Exception e) {
            log("CheckoutServlet Error:" + e.getMessage());
        } finally {
            request.getRequestDispatcher("view/jsp/home/checkout.jsp").forward(request, response);
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
