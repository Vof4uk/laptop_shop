<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<%@ include file="header.jsp"%>
<h2>Welcome, ${account.name}</h2>



<security:authorize access="hasRole('ROLE_ADMIN')">
    <c:url value="/admin/laptops/all" var="laptops_admin"/>
    <p><a href="${laptops_admin}">laptops admin</a></p>

    <c:url value="/admin/brands/all" var="brands_admin"/>
    <p><a href="${brands_admin}">brands admin</a></p>
</security:authorize>

<security:authorize access="hasRole('ROLE_USER')">
    <c:url value="/laptops/all" var="laptops"/>
    <a href="${laptops}">browse our goods!!!</a>
</security:authorize>

<p>Order history</p>
<c:forEach items="${account.orders}" var="order" varStatus="status">
    <p>${status.index + 1}) ${order.id} - ${order.received.toLocalDate()} - ${order.totalCost}</p>
</c:forEach>



</body>
</html>
