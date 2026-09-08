<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách nhân viên</title>
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

    <div class="container">
        <h2>Danh sách nhân viên</h2>

        <div class="toolbar">
            <a class="btn" href="${pageContext.request.contextPath}/employees/add">Thêm mới</a>
        </div>

    <table>
        <thead>
        <tr>
            <th>STT</th>
            <th>Tên nhân viên</th>
            <th>Ngày sinh</th>
            <th>Địa chỉ</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty employees}">
                <tr><td colspan="5" class="empty">Chưa có nhân viên nào</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="e" items="${employees}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${e.name}</td>
                        <td>${e.birthDate}</td>
                        <td>${e.address}</td>
                        <td>
                            <a class="btn" style="padding:5px 10px;" href="${pageContext.request.contextPath}/employees/edit?id=${e.id}">Sửa</a>
                            <a class="btn" style="padding:5px 10px; background:#d9534f;" href="${pageContext.request.contextPath}/employees/delete?id=${e.id}" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
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
