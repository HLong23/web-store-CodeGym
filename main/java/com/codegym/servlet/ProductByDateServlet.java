package com.codegym.servlet;

import com.codegym.dao.ProductDAO;
import com.codegym.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Yêu cầu 5: Xem các sản phẩm được đặt trong khoảng thời gian được chọn
 * Input dạng dd/MM/yyyy (khớp giao diện minh họa __/__/____)
 */
@WebServlet(name = "ProductByDateServlet", urlPatterns = {"/products/by-date"})
public class ProductByDateServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productDAO.findAll();
        req.setAttribute("products", products);

        String fromStr = req.getParameter("from");
        String toStr = req.getParameter("to");

        if (fromStr != null && !fromStr.trim().isEmpty()
                && toStr != null && !toStr.trim().isEmpty()) {
            try {
                inputFormat.setLenient(false);
                Date from = new Date(inputFormat.parse(fromStr).getTime());
                Date to = new Date(inputFormat.parse(toStr).getTime());

                if (from.after(to)) {
                    req.setAttribute("dateError", "Ngày bắt đầu phải trước ngày kết thúc");
                } else {
                    List<Product> byDate = productDAO.findOrderedBetween(from, to);
                    req.setAttribute("byDateProducts", byDate);
                }
            } catch (Exception e) {
                req.setAttribute("dateError", "Định dạng ngày không hợp lệ (dd/MM/yyyy)");
            }
            req.setAttribute("from", fromStr);
            req.setAttribute("to", toStr);
        }

        req.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(req, resp);
    }
}
