package com.codegym.servlet;

import com.codegym.dao.CustomerDAO;
import com.codegym.model.Customer;

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

@WebServlet(name = "CustomerServlet", urlPatterns = {"/customers", "/customers/add", "/customers/edit", "/customers/delete"})
public class CustomerServlet extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        
        if (path.equals("/customers/delete")) {
            handleDelete(req, resp);
        } else if (path.equals("/customers/edit")) {
            handleEditForm(req, resp);
        } else if (path.equals("/customers/add")) {
            handleAddForm(req, resp);
        } else {
            handleList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        
        if (path.equals("/customers/edit")) {
            handleUpdate(req, resp);
        } else {
            handleInsert(req, resp);
        }
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = customerDAO.findAll();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/WEB-INF/views/customerList.jsp").forward(req, resp);
    }

    private void handleAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/customerForm.jsp").forward(req, resp);
    }

    private void handleEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = customerDAO.findById(id);
        if (customer != null) {
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/WEB-INF/views/customerForm.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/customers");
        }
    }

    private void handleInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String birthDateStr = req.getParameter("birthDate");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String email = req.getParameter("email");

        Map<String, String> errors = validate(name, birthDateStr, phone, address, email);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("name", name);
            req.setAttribute("birthDate", birthDateStr);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/customerForm.jsp").forward(req, resp);
            return;
        }

        Customer customer = new Customer(
                name.trim(),
                LocalDate.parse(birthDateStr),
                phone.trim(),
                address.trim(),
                email.trim()
        );
        customerDAO.insert(customer);
        resp.sendRedirect(req.getContextPath() + "/customers");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String birthDateStr = req.getParameter("birthDate");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String email = req.getParameter("email");

        Map<String, String> errors = validate(name, birthDateStr, phone, address, email);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.setAttribute("birthDate", birthDateStr);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/customerForm.jsp").forward(req, resp);
            return;
        }

        Customer customer = new Customer(
                id,
                name.trim(),
                LocalDate.parse(birthDateStr),
                phone.trim(),
                address.trim(),
                email.trim()
        );
        customerDAO.update(customer);
        resp.sendRedirect(req.getContextPath() + "/customers");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        customerDAO.delete(id);
        resp.sendRedirect(req.getContextPath() + "/customers");
    }

    private Map<String, String> validate(String name, String birthDateStr, String phone, String address, String email) {
        Map<String, String> errors = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Tên khách hàng không được để trống");
        }

        if (birthDateStr == null || birthDateStr.trim().isEmpty()) {
            errors.put("birthDate", "Ngày sinh không được để trống");
        } else {
            try {
                LocalDate.parse(birthDateStr);
            } catch (Exception e) {
                errors.put("birthDate", "Định dạng ngày không hợp lệ (yyyy-MM-dd)");
            }
        }

        if (phone == null || phone.trim().isEmpty()) {
            errors.put("phone", "Điện thoại không được để trống");
        }

        if (address == null || address.trim().isEmpty()) {
            errors.put("address", "Địa chỉ không được để trống");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email không được để trống");
        } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            errors.put("email", "Email không hợp lệ");
        }

        return errors;
    }
}
