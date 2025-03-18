/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ddshop.controller.web.shop;

import ddshop.dao.CategoryDAO;
import ddshop.dao.ProductDAO;
import ddshop.model.Categorys;
import ddshop.model.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final String SEARCH_PAGE = "view/jsp/home/shop-list.jsp";
    private static final String SEARCH_IN_SHOPLIST = "view/jsp/ajax/sortproducts.jsp";
    private static final String SORT = "view/jsp/ajax/sortproducts.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SEARCH_PAGE;
        try {
            String txtSearch = request.getParameter("txtSearch");
            String sort_group = request.getParameter("sort_group");
            String scope = request.getParameter("scope");
            ProductDAO pDao = new ProductDAO();
            List<Products> listProducts = pDao.getProductBySearch(txtSearch);
            CategoryDAO cDao = new CategoryDAO();
            List<Categorys> listCategories = cDao.getData();

            if (("shop-list.jsp").equals(scope)) {
                url = SEARCH_IN_SHOPLIST;
            }

            if (sort_group == null) {
                sort_group = txtSearch;
            }

            String valueSort = request.getParameter("valueSort");
            if (valueSort != null) {
                switch (valueSort) {
                    case "1":
                        listProducts = pDao.sortProduct(listProducts, valueSort);
                        break;
                    case "2":
                        listProducts = pDao.sortProduct(listProducts, valueSort);
                        break;
                    case "3":
                        listProducts = pDao.sortProduct(listProducts, valueSort);
                        break;
                }
                url = SORT;
            }
            //Paging
            int page, numPerPage = 9;
            int size = listProducts.size();
            int numberpage = ((size % numPerPage == 0) ? (size / 9) : (size / 9) + 1);
            String xpage = request.getParameter("page");
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            int start, end;
            start = (page - 1) * 9;
            end = Math.min(page * numPerPage, size);

            List<Products> listByPage = pDao.getListByPage(listProducts, start, end);

            request.setAttribute("DATA_FROM", "SearchServlet");
            request.setAttribute("SORT_GROUP", sort_group);
            request.setAttribute("CURRENTPAGE", page);
            request.setAttribute("LISTPRODUCTS", listByPage);
            request.setAttribute("LISTCATEGORIES", listCategories);
            request.setAttribute("VALUESORT", valueSort);
            request.setAttribute("LISTPRODUCTS", listByPage);
            request.setAttribute("LISTCATEGORIES", listCategories);
        } catch (Exception ex) {
            log("SearchServlet error:" + ex.getMessage());
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
