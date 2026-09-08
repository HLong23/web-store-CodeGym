package com.codegym.servlet;

import com.codegym.dao.ProductDAO;
import com.codegym.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Yêu cầu 3: Thêm mới sản phẩm
 * - Name: bắt buộc nhập
 * - Price: bắt buộc nhập, > 100
 * - Discount: chỉ nhận 5, 10, 15, 20 (%)
 * - Stock: bắt buộc nhập, > 10
 */
@WebServlet(name = "ProductAddServlet", urlPatterns = {"/products/add"})
public class ProductAddServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private static final int[] VALID_DISCOUNTS = {5, 10, 15, 20};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/productAdd.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String discountStr = req.getParameter("discount");
        String stockStr = req.getParameter("stock");

        Map<String, String> errors = validate(name, priceStr, discountStr, stockStr);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("name", name);
            req.setAttribute("price", priceStr);
            req.setAttribute("discount", discountStr);
            req.setAttribute("stock", stockStr);
            req.getRequestDispatcher("/WEB-INF/views/productAdd.jsp").forward(req, resp);
            return;
        }

        Product product = new Product(
                name.trim(),
                Double.parseDouble(priceStr),
                Integer.parseInt(discountStr),
                Integer.parseInt(stockStr)
        );
        productDAO.insert(product);

        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private Map<String, String> validate(String name, String priceStr, String discountStr, String stockStr) {
        Map<String, String> errors = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Tên sản phẩm không được để trống");
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 100) {
                errors.put("price", "Giá phải lớn hơn 100");
            }
        } catch (NumberFormatException | NullPointerException e) {
            errors.put("price", "Giá không được để trống và phải là số");
        }

        try {
            int discount = Integer.parseInt(discountStr);
            boolean valid = false;
            for (int d : VALID_DISCOUNTS) {
                if (d == discount) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                errors.put("discount", "Giảm giá chỉ được nhận giá trị 5%, 10%, 15%, 20%");
            }
        } catch (NumberFormatException | NullPointerException e) {
            errors.put("discount", "Giảm giá không hợp lệ");
        }

        try {
            int stock = Integer.parseInt(stockStr);
            if (stock <= 10) {
                errors.put("stock", "Tồn kho phải lớn hơn 10");
            }
        } catch (NumberFormatException | NullPointerException e) {
            errors.put("stock", "Tồn kho không được để trống và phải là số");
        }

        return errors;
    }
}
