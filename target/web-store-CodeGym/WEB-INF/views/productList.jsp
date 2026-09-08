<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sản phẩm</title>
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

    <div class="container">
        <h2>Danh sách sản phẩm</h2>

        <div class="toolbar">
            <a class="btn" href="${pageContext.request.contextPath}/products/add">Thêm mới</a>

            <form method="get" action="${pageContext.request.contextPath}/products/top">
                <span>Danh sách top:</span>
                <select name="top">
                    <option value="3" ${selectedTop == 3 ? 'selected' : ''}>3</option>
                    <option value="5" ${selectedTop == 5 ? 'selected' : ''}>5</option>
                    <option value="10" ${selectedTop == 10 ? 'selected' : ''}>10</option>
                </select>
                <span>Sản phẩm được đặt hàng nhiều nhất</span>
                <button class="btn" type="submit">Xem</button>
            </form>
        </div>

        <div class="toolbar">
            <form method="get" action="${pageContext.request.contextPath}/products/by-date">
                <span>Danh sách sản phẩm được đặt từ:</span>
                <input type="text" name="from" placeholder="dd/mm/yyyy" value="${from}">
                <span>đến:</span>
                <input type="text" name="to" placeholder="dd/mm/yyyy" value="${to}">
                <button class="btn" type="submit">Xem</button>
            </form>
        </div>
        <c:if test="${not empty dateError}">
            <div class="error">${dateError}</div>
        </c:if>

    <table>
        <thead>
        <tr>
            <th>STT</th>
            <th>Name</th>
            <th>Price</th>
            <th>Discount</th>
            <th>Stock</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty products}">
                <tr><td colspan="5" class="empty">Chưa có sản phẩm nào</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="p" items="${products}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${p.name}</td>
                        <td><fmt:formatNumber value="${p.price}" type="number"/></td>
                        <td>${p.discount}%</td>
                        <td>${p.stock}</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <c:if test="${not empty topProducts}">
        <div class="section-title">Top ${selectedTop} sản phẩm bán chạy nhất</div>
        <table>
            <thead>
            <tr>
                <th>STT</th>
                <th>Name</th>
                <th>Price</th>
                <th>Discount</th>
                <th>Stock</th>
                <th>Số lượng đã đặt</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${topProducts}" varStatus="i">
                <tr>
                    <td>${i.index + 1}</td>
                    <td>${p.name}</td>
                    <td><fmt:formatNumber value="${p.price}" type="number"/></td>
                    <td>${p.discount}%</td>
                    <td>${p.stock}</td>
                    <td>${p.orderCount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${not empty byDateProducts}">
        <div class="section-title">Sản phẩm được đặt hàng từ ${from} đến ${to}</div>
        <table>
            <thead>
            <tr>
                <th>STT</th>
                <th>Name</th>
                <th>Price</th>
                <th>Discount</th>
                <th>Stock</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${byDateProducts}" varStatus="i">
                <tr>
                    <td>${i.index + 1}</td>
                    <td>${p.name}</td>
                    <td><fmt:formatNumber value="${p.price}" type="number"/></td>
                    <td>${p.discount}%</td>
                    <td>${p.stock}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${param.from != null && param.to != null && empty byDateProducts && empty dateError}">
        <div class="section-title">Sản phẩm được đặt hàng từ ${from} đến ${to}</div>
        <p class="empty">Không có sản phẩm nào được đặt trong khoảng thời gian này</p>
    </c:if>
    </div>
</div>
</body>
</html>
