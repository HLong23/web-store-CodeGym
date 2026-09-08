<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty customer ? 'Thêm mới' : 'Sửa'} khách hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="sidebar">
    <ul>
        <li><a href="${pageContext.request.contextPath}/products">Quản lý sản phẩm</a></li>
        <li><a href="${pageContext.request.contextPath}/employees">Quản lý nhân viên</a></li>
        <li><a href="${pageContext.request.contextPath}/customers" class="active">Quản lý khách hàng</a></li>
        <li><a href="${pageContext.request.contextPath}/orders">Quản lý đơn hàng</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="header">
        <h1>CodeGym Shop - Quản trị khách hàng</h1>
    </div>

    <div class="container" style="max-width: 480px;">
        <h2>${empty customer ? 'Thêm mới' : 'Sửa'} khách hàng</h2>

    <form method="post" action="${pageContext.request.contextPath}/customers${empty customer ? '/add' : '/edit'}">
        <c:if test="${not empty customer}">
            <input type="hidden" name="id" value="${customer.id}">
        </c:if>

        <div class="form-group">
            <label>Tên khách hàng <span class="required">(*)</span></label>
            <input type="text" name="name" value="${empty customer ? name : customer.name}">
            <c:if test="${not empty errors.name}"><div class="error">${errors.name}</div></c:if>
        </div>

        <div class="form-group">
            <label>Ngày sinh <span class="required">(*)</span></label>
            <input type="date" name="birthDate" value="${empty customer ? birthDate : customer.birthDate}">
            <c:if test="${not empty errors.birthDate}"><div class="error">${errors.birthDate}</div></c:if>
        </div>

        <div class="form-group">
            <label>Điện thoại <span class="required">(*)</span></label>
            <input type="text" name="phone" value="${empty customer ? phone : customer.phone}">
            <c:if test="${not empty errors.phone}"><div class="error">${errors.phone}</div></c:if>
        </div>

        <div class="form-group">
            <label>Địa chỉ <span class="required">(*)</span></label>
            <input type="text" name="address" value="${empty customer ? address : customer.address}">
            <c:if test="${not empty errors.address}"><div class="error">${errors.address}</div></c:if>
        </div>

        <div class="form-group">
            <label>Email <span class="required">(*)</span></label>
            <input type="email" name="email" value="${empty customer ? email : customer.email}">
            <c:if test="${not empty errors.email}"><div class="error">${errors.email}</div></c:if>
        </div>

        <button class="btn" type="submit">${empty customer ? 'Thêm' : 'Cập nhật'}</button>
        <a class="btn" style="background:#888;" href="${pageContext.request.contextPath}/customers">Hủy</a>
    </form>
    </div>
</div>
</body>
</html>
