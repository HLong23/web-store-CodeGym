package com.codegym.servlet;

import com.codegym.dao.OrderDAO;
import com.codegym.dao.CustomerDAO;
import com.codegym.dao.EmployeeDAO;
import com.codegym.dao.ProductDAO;
import com.codegym.model.Order;
import com.codegym.model.OrderDetail;
import com.codegym.model.Customer;
import com.codegym.model.Employee;
import com.codegym.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OrderServlet", urlPatterns = {"/orders", "/orders/add", "/orders/view", "/orders/delete"})
public class OrderServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        
        if (path.equals("/orders/delete")) {
            handleDelete(req, resp);
        } else if (path.equals("/orders/view")) {
            handleView(req, resp);
        } else if (path.equals("/orders/add")) {
            handleAddForm(req, resp);
        } else {
            handleList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleInsert(req, resp);
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderDAO.findAll();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orderList.jsp").forward(req, resp);
    }

    private void handleAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = customerDAO.findAll();
        List<Employee> employees = employeeDAO.findAll();
        List<Product> products = productDAO.findAll();
        
        req.setAttribute("customers", customers);
        req.setAttribute("employees", employees);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/orderForm.jsp").forward(req, resp);
    }

    private void handleView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Order order = orderDAO.findById(id);
        if (order != null) {
            List<OrderDetail> orderDetails = orderDAO.findOrderDetails(id);
            req.setAttribute("order", order);
            req.setAttribute("orderDetails", orderDetails);
            req.getRequestDispatcher("/WEB-INF/views/orderDetail.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/orders");
        }
    }

    private void handleInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paymentMethod = req.getParameter("paymentMethod");
        String customerIdStr = req.getParameter("customerId");
        String employeeIdStr = req.getParameter("employeeId");
        String orderDateStr = req.getParameter("orderDate");
        String deliveryDateStr = req.getParameter("deliveryDate");
        String deliveryAddress = req.getParameter("deliveryAddress");
        
        String[] productIds = req.getParameterValues("productId");
        String[] quantities = req.getParameterValues("quantity");

        Map<String, String> errors = validate(paymentMethod, customerIdStr, employeeIdStr, 
                orderDateStr, deliveryAddress, productIds, quantities);

        if (!errors.isEmpty()) {
            List<Customer> customers = customerDAO.findAll();
            List<Employee> employees = employeeDAO.findAll();
            List<Product> products = productDAO.findAll();
            
            req.setAttribute("errors", errors);
            req.setAttribute("paymentMethod", paymentMethod);
            req.setAttribute("customerId", customerIdStr);
            req.setAttribute("employeeId", employeeIdStr);
            req.setAttribute("orderDate", orderDateStr);
            req.setAttribute("deliveryDate", deliveryDateStr);
            req.setAttribute("deliveryAddress", deliveryAddress);
            req.setAttribute("customers", customers);
            req.setAttribute("employees", employees);
            req.setAttribute("products", products);
            req.getRequestDispatcher("/WEB-INF/views/orderForm.jsp").forward(req, resp);
            return;
        }

        Order order = new Order(
                paymentMethod,
                Integer.parseInt(customerIdStr),
                Integer.parseInt(employeeIdStr),
                LocalDate.parse(orderDateStr),
                deliveryDateStr != null && !deliveryDateStr.isEmpty() ? LocalDate.parse(deliveryDateStr) : null,
                deliveryAddress.trim()
        );
        
        orderDAO.insert(order);
        
        if (productIds != null && quantities != null) {
            for (int i = 0; i < productIds.length; i++) {
                if (!productIds[i].isEmpty() && !quantities[i].isEmpty()) {
                    OrderDetail detail = new OrderDetail(
                            order.getId(),
                            Integer.parseInt(productIds[i]),
                            Integer.parseInt(quantities[i])
                    );
                    orderDAO.insertOrderDetail(detail);
                }
            }
        }
        
        resp.sendRedirect(req.getContextPath() + "/orders");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        orderDAO.delete(id);
        resp.sendRedirect(req.getContextPath() + "/orders");
    }

    private Map<String, String> validate(String paymentMethod, String customerIdStr, String employeeIdStr,
                                          String orderDateStr, String deliveryAddress, String[] productIds, String[] quantities) {
        Map<String, String> errors = new HashMap<>();

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            errors.put("paymentMethod", "Phương thức thanh toán không được để trống");
        }

        if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
            errors.put("customerId", "Khách hàng không được để trống");
        }

        if (employeeIdStr == null || employeeIdStr.trim().isEmpty()) {
            errors.put("employeeId", "Nhân viên không được để trống");
        }

        if (orderDateStr == null || orderDateStr.trim().isEmpty()) {
            errors.put("orderDate", "Ngày đặt hàng không được để trống");
        } else {
            try {
                LocalDate.parse(orderDateStr);
            } catch (Exception e) {
                errors.put("orderDate", "Định dạng ngày không hợp lệ (yyyy-MM-dd)");
            }
        }

        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            errors.put("deliveryAddress", "Địa chỉ giao hàng không được để trống");
        }

        boolean hasProducts = false;
        if (productIds != null && quantities != null) {
            for (int i = 0; i < productIds.length; i++) {
                if (!productIds[i].isEmpty() && !quantities[i].isEmpty()) {
                    hasProducts = true;
                    try {
                        int quantity = Integer.parseInt(quantities[i]);
                        if (quantity <= 0) {
                            errors.put("quantity", "Số lượng phải lớn hơn 0");
                        }
                    } catch (NumberFormatException e) {
                        errors.put("quantity", "Số lượng phải là số");
                    }
                }
            }
        }

        if (!hasProducts) {
            errors.put("products", "Phải chọn ít nhất một sản phẩm");
        }

        return errors;
    }
}
