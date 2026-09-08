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
 * Yêu cầu 2: hiển thị toàn bộ danh sách sản phẩm
 */
@WebServlet(name = "ProductListServlet", urlPatterns = {"", "/products"})
public class ProductListServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productDAO.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(req, resp);
    }
}
