/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ddshop.controller.web.shop;

import ddshop.dao.ProductDAO;
import ddshop.model.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MY PC
 */
@WebServlet(name="SingleProductServlet", urlPatterns={"/SingleProductServlet"})
public class SingleProductServlet extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            ProductDAO pDao = new ProductDAO();
            String product_id = request.getParameter("product_id");
            Products product = pDao.getProductById(Integer.parseInt(product_id));
            List<Products> listProduct = pDao.getProductByCategoryId(pDao.getData(),product.getCategory().getId());
            List<Products> listProductSameCategory = new ArrayList<>();
            int count = 0;
            for(Products p : listProduct){
                if(product.getId() != p.getId()){
                    listProductSameCategory.add(p);
                    count++;
                    if(count ==4){
                        break;
                    }
                }
            }
            request.setAttribute("PRODUCT", product);
            request.setAttribute("LIST_PRODUCTS_SAME_CATEGORY", listProductSameCategory);
        } catch (Exception e) {
            
        }finally{
            request.getRequestDispatcher("view/jsp/home/single-product.jsp").forward(request, response);
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
