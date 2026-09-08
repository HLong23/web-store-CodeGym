<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách khách hàng</title>
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

    <div class="container">
        <h2>Danh sách khách hàng</h2>

        <div class="toolbar">
            <a class="btn" href="${pageContext.request.contextPath}/customers/add">Thêm mới</a>
        </div>

    <table>
        <thead>
        <tr>
            <th>STT</th>
            <th>Tên khách hàng</th>
            <th>Ngày sinh</th>
            <th>Điện thoại</th>
            <th>Địa chỉ</th>
            <th>Email</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty customers}">
                <tr><td colspan="7" class="empty">Chưa có khách hàng nào</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="c" items="${customers}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${c.name}</td>
                        <td>${c.birthDate}</td>
                        <td>${c.phone}</td>
                        <td>${c.address}</td>
                        <td>${c.email}</td>
                        <td>
                            <a class="btn" style="padding:5px 10px;" href="${pageContext.request.contextPath}/customers/edit?id=${c.id}">Sửa</a>
                            <a class="btn" style="padding:5px 10px; background:#d9534f;" href="${pageContext.request.contextPath}/customers/delete?id=${c.id}" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
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
