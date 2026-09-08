package com.codegym.servlet;

import com.codegym.dao.EmployeeDAO;
import com.codegym.model.Employee;

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

@WebServlet(name = "EmployeeServlet", urlPatterns = {"/employees", "/employees/add", "/employees/edit", "/employees/delete"})
public class EmployeeServlet extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        
        if (path.equals("/employees/delete")) {
            handleDelete(req, resp);
        } else if (path.equals("/employees/edit")) {
            handleEditForm(req, resp);
        } else if (path.equals("/employees/add")) {
            handleAddForm(req, resp);
        } else {
            handleList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        
        if (path.equals("/employees/edit")) {
            handleUpdate(req, resp);
        } else {
            handleInsert(req, resp);
        }
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = employeeDAO.findAll();
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/WEB-INF/views/employeeList.jsp").forward(req, resp);
    }

    private void handleAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(req, resp);
    }

    private void handleEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            req.setAttribute("employee", employee);
            req.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/employees");
        }
    }

    private void handleInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String birthDateStr = req.getParameter("birthDate");
        String address = req.getParameter("address");

        Map<String, String> errors = validate(name, birthDateStr, address);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("name", name);
            req.setAttribute("birthDate", birthDateStr);
            req.setAttribute("address", address);
            req.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(req, resp);
            return;
        }

        Employee employee = new Employee(
                name.trim(),
                LocalDate.parse(birthDateStr),
                address.trim()
        );
        employeeDAO.insert(employee);
        resp.sendRedirect(req.getContextPath() + "/employees");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String birthDateStr = req.getParameter("birthDate");
        String address = req.getParameter("address");

        Map<String, String> errors = validate(name, birthDateStr, address);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.setAttribute("birthDate", birthDateStr);
            req.setAttribute("address", address);
            req.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(req, resp);
            return;
        }

        Employee employee = new Employee(
                id,
                name.trim(),
                LocalDate.parse(birthDateStr),
                address.trim()
        );
        employeeDAO.update(employee);
        resp.sendRedirect(req.getContextPath() + "/employees");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        employeeDAO.delete(id);
        resp.sendRedirect(req.getContextPath() + "/employees");
    }

    private Map<String, String> validate(String name, String birthDateStr, String address) {
        Map<String, String> errors = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Tên nhân viên không được để trống");
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

        if (address == null || address.trim().isEmpty()) {
            errors.put("address", "Địa chỉ không được để trống");
        }

        return errors;
    }
}
