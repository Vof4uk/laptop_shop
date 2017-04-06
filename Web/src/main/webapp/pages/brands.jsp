<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Brands</title>
</head>
<body>
<%@ include file="header.jsp"%>
<h1>Brands</h1>
<h2>${pageContext.request.contextPath}</h2>
<c:forEach var="brand" items="${brands}">
    <jsp:useBean id="brand" type="ua.mykytenko.entities.Brand"/>
    <c:url value="/admin/brands/edit?id=${brand.id}" var="edit_url"/>
    <c:url value="/admin/brands/delete?id=${brand.id}" var="delete_url"/>
    <span>
        <h4>${brand.shortName}</h4>
        <h5>${brand.fullName}</h5>
        <p>${brand.description}</p>
        <a href="${edit_url}">edit</a>
        <a href="${delete_url}">delete</a>
    </span>
</c:forEach>

</body>
</html>