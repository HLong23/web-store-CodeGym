<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tạo đơn hàng mới</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .product-row { margin-bottom: 10px; }
        .product-row input { width: 80px; }
    </style>
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

    <div class="container" style="max-width: 600px;">
        <h2>Tạo đơn hàng mới</h2>

    <form method="post" action="${pageContext.request.contextPath}/orders/add">
        <div class="form-group">
            <label>Khách hàng <span class="required">(*)</span></label>
            <select name="customerId">
                <option value="">-- Chọn khách hàng --</option>
                <c:forEach var="c" items="${customers}">
                    <option value="${c.id}" ${customerId == c.id ? 'selected' : ''}>${c.name}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty errors.customerId}"><div class="error">${errors.customerId}</div></c:if>
        </div>

        <div class="form-group">
            <label>Nhân viên <span class="required">(*)</span></label>
            <select name="employeeId">
                <option value="">-- Chọn nhân viên --</option>
                <c:forEach var="e" items="${employees}">
                    <option value="${e.id}" ${employeeId == e.id ? 'selected' : ''}>${e.name}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty errors.employeeId}"><div class="error">${errors.employeeId}</div></c:if>
        </div>

        <div class="form-group">
            <label>Phương thức thanh toán <span class="required">(*)</span></label>
            <select name="paymentMethod">
                <option value="">-- Chọn phương thức --</option>
                <option value="Tien mat" ${paymentMethod == 'Tien mat' ? 'selected' : ''}>Tiền mặt</option>
                <option value="Chuyen khoan" ${paymentMethod == 'Chuyen khoan' ? 'selected' : ''}>Chuyển khoản</option>
                <option value="Vi dien tu" ${paymentMethod == 'Vi dien tu' ? 'selected' : ''}>Ví điện tử</option>
            </select>
            <c:if test="${not empty errors.paymentMethod}"><div class="error">${errors.paymentMethod}</div></c:if>
        </div>

        <div class="form-group">
            <label>Ngày đặt hàng <span class="required">(*)</span></label>
            <input type="date" name="orderDate" value="${orderDate}">
            <c:if test="${not empty errors.orderDate}"><div class="error">${errors.orderDate}</div></c:if>
        </div>

        <div class="form-group">
            <label>Ngày giao hàng</label>
            <input type="date" name="deliveryDate" value="${deliveryDate}">
        </div>

        <div class="form-group">
            <label>Địa chỉ giao hàng <span class="required">(*)</span></label>
            <input type="text" name="deliveryAddress" value="${deliveryAddress}">
            <c:if test="${not empty errors.deliveryAddress}"><div class="error">${errors.deliveryAddress}</div></c:if>
        </div>

        <div class="form-group">
            <label>Sản phẩm <span class="required">(*)</span></label>
            <div id="products-container">
                <div class="product-row">
                    <select name="productId">
                        <option value="">-- Chọn sản phẩm --</option>
                        <c:forEach var="p" items="${products}">
                            <option value="${p.id}">${p.name} - ${p.price} VNĐ</option>
                        </c:forEach>
                    </select>
                    <input type="number" name="quantity" placeholder="Số lượng" min="1">
                </div>
            </div>
            <button type="button" class="btn" style="margin-top:5px;" onclick="addProductRow()">+ Thêm sản phẩm</button>
            <c:if test="${not empty errors.products}"><div class="error">${errors.products}</div></c:if>
            <c:if test="${not empty errors.quantity}"><div class="error">${errors.quantity}</div></c:if>
        </div>

        <button class="btn" type="submit">Tạo đơn hàng</button>
        <a class="btn" style="background:#888;" href="${pageContext.request.contextPath}/orders">Hủy</a>
    </form>
    </div>
</div>

<script>
function addProductRow() {
    const container = document.getElementById('products-container');
    const newRow = document.createElement('div');
    newRow.className = 'product-row';
    newRow.innerHTML = `
        <select name="productId">
            <option value="">-- Chọn sản phẩm --</option>
            <c:forEach var="p" items="${products}">
                <option value="${p.id}">${p.name} - ${p.price} VNĐ</option>
            </c:forEach>
        </select>
        <input type="number" name="quantity" placeholder="Số lượng" min="1">
        <button type="button" onclick="this.parentElement.remove()" style="background:#d9534f; color:white; border:none; padding:5px 10px;">X</button>
    `;
    container.appendChild(newRow);
}
</script>
</body>
</html>
