<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty employee ? 'Thêm mới' : 'Sửa'} nhân viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="sidebar">
    <ul>
        <li><a href="${pageContext.request.contextPath}/products">Quản lý sản phẩm</a></li>
        <li><a href="${pageContext.request.contextPath}/employees" class="active">Quản lý nhân viên</a></li>
        <li><a href="${pageContext.request.contextPath}/customers">Quản lý khách hàng</a></li>
        <li><a href="${pageContext.request.contextPath}/orders">Quản lý đơn hàng</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="header">
        <h1>CodeGym Shop - Quản trị nhân viên</h1>
    </div>

    <div class="container" style="max-width: 480px;">
        <h2>${empty employee ? 'Thêm mới' : 'Sửa'} nhân viên</h2>

    <form method="post" action="${pageContext.request.contextPath}/employees${empty employee ? '/add' : '/edit'}">
        <c:if test="${not empty employee}">
            <input type="hidden" name="id" value="${employee.id}">
        </c:if>

        <div class="form-group">
            <label>Tên nhân viên <span class="required">(*)</span></label>
            <input type="text" name="name" value="${empty employee ? name : employee.name}">
            <c:if test="${not empty errors.name}"><div class="error">${errors.name}</div></c:if>
        </div>

        <div class="form-group">
            <label>Ngày sinh <span class="required">(*)</span></label>
            <input type="date" name="birthDate" value="${empty employee ? birthDate : employee.birthDate}">
            <c:if test="${not empty errors.birthDate}"><div class="error">${errors.birthDate}</div></c:if>
        </div>

        <div class="form-group">
            <label>Địa chỉ <span class="required">(*)</span></label>
            <input type="text" name="address" value="${empty employee ? address : employee.address}">
            <c:if test="${not empty errors.address}"><div class="error">${errors.address}</div></c:if>
        </div>

        <button class="btn" type="submit">${empty employee ? 'Thêm' : 'Cập nhật'}</button>
        <a class="btn" style="background:#888;" href="${pageContext.request.contextPath}/employees">Hủy</a>
    </form>
    </div>
</div>
</body>
</html>
