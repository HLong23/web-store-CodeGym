<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm mới sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="sidebar">
    <ul>
        <li><a href="${pageContext.request.contextPath}/products" class="active">Quản lý sản phẩm</a></li>
        <li><a href="${pageContext.request.contextPath}/employees">Quản lý nhân viên</a></li>
        <li><a href="${pageContext.request.contextPath}/customers">Quản lý khách hàng</a></li>
        <li><a href="${pageContext.request.contextPath}/orders">Quản lý đơn hàng</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="header">
        <h1>CodeGym Shop - Quản trị sản phẩm</h1>
    </div>

    <div class="container" style="max-width: 480px;">
        <h2>Thêm mới sản phẩm</h2>

        <form method="post" action="${pageContext.request.contextPath}/products/add">

            <div class="form-group">
                <label>Name <span class="required">(*)</span></label>
                <input type="text" name="name" value="${name}">
                <c:if test="${not empty errors.name}"><div class="error">${errors.name}</div></c:if>
            </div>

            <div class="form-group">
                <label>Price <span class="required">(*)</span></label>
                <input type="text" name="price" value="${price}">
                <c:if test="${not empty errors.price}"><div class="error">${errors.price}</div></c:if>
            </div>

            <div class="form-group">
                <label>Discount <span class="required">(*)</span></label>
                <select name="discount">
                    <option value="5" ${discount == '5' ? 'selected' : ''}>5 (%)</option>
                    <option value="10" ${discount == '10' ? 'selected' : ''}>10 (%)</option>
                    <option value="15" ${discount == '15' ? 'selected' : ''}>15 (%)</option>
                    <option value="20" ${discount == '20' ? 'selected' : ''}>20 (%)</option>
                </select>
                <c:if test="${not empty errors.discount}"><div class="error">${errors.discount}</div></c:if>
            </div>

            <div class="form-group">
                <label>Stock <span class="required">(*)</span></label>
                <input type="text" name="stock" value="${stock}">
                <c:if test="${not empty errors.stock}"><div class="error">${errors.stock}</div></c:if>
            </div>

            <button class="btn" type="submit">Thêm</button>
            <a class="btn" style="background:#888;" href="${pageContext.request.contextPath}/products">Hủy</a>
        </form>
    </div>
</div>
</body>
</html>
