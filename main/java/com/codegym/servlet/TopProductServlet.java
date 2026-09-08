package com.codegym.servlet;

import com.codegym.dao.ProductDAO;
import com.codegym.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Yêu cầu 4: Xem top sản phẩm bán chạy nhất (Top 3, 5, 10)
 */
@WebServlet(name = "TopProductServlet", urlPatterns = {"/products/top"})
public class TopProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int top = 3;
        String topParam = req.getParameter("top");
        if (topParam != null) {
            try {
                top = Integer.parseInt(topParam);
            } catch (NumberFormatException ignored) {
            }
        }

        List<Product> products = productDAO.findAll();
        List<Product> topProducts = productDAO.findTopOrdered(top);

        req.setAttribute("products", products);
        req.setAttribute("topProducts", topProducts);
        req.setAttribute("selectedTop", top);
        req.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(req, resp);
    }
}
