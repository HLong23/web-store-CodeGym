<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
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
        <h2>Chi tiết đơn hàng #${order.id}</h2>

    <div class="section-title">Thông tin đơn hàng</div>
    <table>
        <tr>
            <th>Khách hàng:</th>
            <td>${order.customer.name}</td>
        </tr>
        <tr>
            <th>Nhân viên:</th>
            <td>${order.employee.name}</td>
        </tr>
        <tr>
            <th>Phương thức thanh toán:</th>
            <td>${order.paymentMethod}</td>
        </tr>
        <tr>
            <th>Ngày đặt hàng:</th>
            <td>${order.orderDate}</td>
        </tr>
        <tr>
            <th>Ngày giao hàng:</th>
            <td>${order.deliveryDate}</td>
        </tr>
        <tr>
            <th>Địa chỉ giao hàng:</th>
            <td>${order.deliveryAddress}</td>
        </tr>
    </table>

    <div class="section-title">Chi tiết sản phẩm</div>
    <table>
        <thead>
        <tr>
            <th>STT</th>
            <th>Tên sản phẩm</th>
            <th>Giá</th>
            <th>Giảm giá</th>
            <th>Số lượng</th>
            <th>Thành tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty orderDetails}">
                <tr><td colspan="6" class="empty">Không có sản phẩm nào</td></tr>
            </c:when>
            <c:otherwise>
                <c:set var="total" value="0"/>
                <c:forEach var="od" items="${orderDetails}" varStatus="i">
                    <c:set var="subtotal" value="${od.product.price * od.quantity * (100 - od.product.discount) / 100}"/>
                    <c:set var="total" value="${total + subtotal}"/>
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${od.product.name}</td>
                        <td><fmt:formatNumber value="${od.product.price}" type="number"/></td>
                        <td>${od.product.discount}%</td>
                        <td>${od.quantity}</td>
                        <td><fmt:formatNumber value="${subtotal}" type="number"/></td>
                    </tr>
                </c:forEach>
                <tr style="font-weight:bold; background:#f0f0f0;">
                    <td colspan="5" style="text-align:right;">Tổng tiền:</td>
                    <td><fmt:formatNumber value="${total}" type="number"/> VNĐ</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <div class="toolbar">
        <a class="btn" href="${pageContext.request.contextPath}/orders">Quay lại danh sách</a>
    </div>
    </div>
</div>
</body>
</html>
