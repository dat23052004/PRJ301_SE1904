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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "FilterServlet", urlPatterns = {"/FilterServlet"})
public class FilterServlet extends HttpServlet {

    private static final String SHOP_LIST = "view/jsp/home/shop-list.jsp";
    private static final String SORT = "view/jsp/ajax/sortproducts.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SHOP_LIST;
        try {
            ProductDAO pDao = new ProductDAO();
            CategoryDAO cDao = new CategoryDAO();
            List<Products> pList = pDao.getData();
            List<Categorys> cList = cDao.getData();

            String group = request.getParameter("sort_group"); // url SORT
            String action = request.getParameter("btnAction");
            String id_filter_raw = request.getParameter("id_filter");
            String[] mult_id_filter_raw = request.getParameterValues("id_filter");
            int[] mult_id_filter = null; // chứa các danh mục   
            Boolean[] chid = new Boolean[cList.size() + 1]; // kiểm tra danh mục nào đang đc chọn
            int id_filter = 0; // danh mục 
            String queryString = request.getQueryString();

            if (action == null) {
                action = group;
            }

            //CategoryId 
            if (id_filter_raw != null) {
                id_filter = Integer.parseInt(id_filter_raw);
                if ("filterByCategory".equals(action)) {
                    if (mult_id_filter_raw != null) {
                        mult_id_filter = new int[mult_id_filter_raw.length];
                        for (int i = 0; i < mult_id_filter.length; i++) {
                            mult_id_filter[i] = Integer.parseInt(mult_id_filter_raw[i]);
                        }

                        pList = pDao.searchByCheckBox(pList, mult_id_filter);
                        if (id_filter == 0) {
                            chid[0] = true;
                        }
                    }

                } else if ("filterBySupplier".equals(action)) {
                    pList = pDao.getProductSupplierId(id_filter);
                }

            } else if (id_filter_raw == null || id_filter == 0) {
                chid[0] = true;
            }

            if (mult_id_filter == null && mult_id_filter_raw != null) {
                mult_id_filter = new int[mult_id_filter_raw.length];
                mult_id_filter[0] = 0;
            }

            // Sort Product
            String valueSort = request.getParameter("valueSort");
            if (valueSort != null && !valueSort.equals("")) {
                switch (valueSort) {
                    case "1":
                        pList = pDao.sortProduct(pList, valueSort);
                        break;
                    case "2":
                        pList = pDao.sortProduct(pList, valueSort);
                        break;
                    case "3":
                        pList = pDao.sortProduct(pList, valueSort);
                        break;
                }
                url = SORT;
            }
            
            // Price
            String priceFrom_raw = request.getParameter("pricefrom");
            String priceTo_raw = request.getParameter("priceto");
            double priceFrom = ((priceFrom_raw == null || "".equals(priceFrom_raw)) ? 0 : Double.parseDouble(priceFrom_raw));
            double priceTo = ((priceTo_raw == null || "".equals(priceTo_raw)) ? 0 : Double.parseDouble(priceTo_raw));
            if (priceFrom != 0 || priceTo != 0) {
                pList = pDao.searchByPrice(pList, priceFrom, priceTo);
                request.setAttribute("price1", priceFrom);
                request.setAttribute("price2", priceTo);
                url = SHOP_LIST;
            }

            //Color
            String color = request.getParameter("color");
            if (color != null && !color.equals("")) {
                pList = pDao.searchByColor(pList, color);
                url = SHOP_LIST;
            }

            //Discount
            String discount = request.getParameter("discount");
            if (discount != null) {
                switch (discount) {
                    case "dis25":
                        pList = pDao.searchByDiscount(pList, 0.25);
                        break;
                    case "dis40":
                        pList = pDao.searchByDiscount(pList, 0.4);
                        break;
                    case "dis75":
                        pList = pDao.searchByDiscount(pList, 0.75);
                        break;
                    default:
                        break;
                }
                request.setAttribute("DISCOUNT", discount);
                url = SHOP_LIST;
            }

            //Paging
            int page, numPerPage = 9;
            int size = pList.size();
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

            List<Products> listByPage = pDao.getListByPage(pList, start, end);

            //RefineBrand
            if ((mult_id_filter_raw != null) && (mult_id_filter[0] != 0)) {
                chid[0] = false;
                for (int i = 1; i < chid.length; i++) {
                    if (isCheck(cList.get(i - 1).getId(), mult_id_filter)) {
                        chid[i] = true;
                    } else {
                        chid[i] = false;
                    }
                }
            }

            request.setAttribute("CORLOR", color);
            request.setAttribute("SORT_GROUP", action);
            request.setAttribute("DATA_FROM", "FilterServlet");
            request.setAttribute("NUMBERPAGE", numberpage);
            request.setAttribute("CURRENTPAGE", page);
            request.setAttribute("chid", chid);
            request.setAttribute("CURRENTSERVLET", "Shop");
            request.setAttribute("CIDCHECK", id_filter_raw);
            request.setAttribute("LISTPRODUCTS", listByPage);
            request.setAttribute("LISTCATEGORIES", cList);
            request.setAttribute("VALUESORT", valueSort);
            request.setAttribute("filterByCategory", "filterByCategory");
            request.setAttribute("QUERYSTRING", queryString);

        } catch (Exception e) {
            log("FilterServlet error:" + e.getMessage());
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

    private boolean isCheck(int d, int[] id) {
        if (id == null) {
            return false;
        } else {
            for (int i = 0; i < id.length; i++) {
                if (id[i] == d) {
                    return true;
                }
            }
        }
        return false;
    }

}
