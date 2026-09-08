<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="sidebar">
    <ul>
        <li><a href="${pageContext.request.contextPath}/products">Quản lý sản phẩm</a></li>
        <li><a href="${pageContext.request.contextPath}/employees">Quản lý nhân viên</a></li>
        <li><a href="${pageContext.request.contextPath}/customers">Quản lý khách hàng</a></li>
        <li><a href="${pageContext.request.contextPath}/orders" class="active">Quản lý đơn hàng</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="header">
        <h1>CodeGym Shop - Quản trị đơn hàng</h1>
    </div>

    <div class="container">
        <h2>Danh sách đơn hàng</h2>

        <div class="toolbar">
            <a class="btn" href="${pageContext.request.contextPath}/orders/add">Tạo đơn hàng mới</a>
        </div>

    <table>
        <thead>
        <tr>
            <th>STT</th>
            <th>Khách hàng</th>
            <th>Nhân viên</th>
            <th>Phương thức thanh toán</th>
            <th>Ngày đặt</th>
            <th>Ngày giao</th>
            <th>Địa chỉ giao</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty orders}">
                <tr><td colspan="8" class="empty">Chưa có đơn hàng nào</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="o" items="${orders}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${o.customer.name}</td>
                        <td>${o.employee.name}</td>
                        <td>${o.paymentMethod}</td>
                        <td>${o.orderDate}</td>
                        <td>${o.deliveryDate}</td>
                        <td>${o.deliveryAddress}</td>
                        <td>
                            <a class="btn" style="padding:5px 10px;" href="${pageContext.request.contextPath}/orders/view?id=${o.id}">Xem chi tiết</a>
                            <a class="btn" style="padding:5px 10px; background:#d9534f;" href="${pageContext.request.contextPath}/orders/delete?id=${o.id}" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    </div>
</div>
</body>
</html>
