<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
    <title>laptops</title>
</head>
<body>
<%@ include file="header.jsp" %>
<h1>laptops</h1>
<h2>${pageContext.request.contextPath}</h2>
<h2>${msg}</h2>
<c:forEach var="laptop" items="${laptops}">
    <jsp:useBean id="laptop" type="ua.mykytenko.entities.Laptop"/>
    <c:url var="pic" value="/${laptop.imagesLocations[0]}"/>
    <span>
        <h4>${laptop.model} ---- costs: ${laptop.price} ---- ${laptop.stock} available</h4>
        <c:url var="show_details" value="/laptops/show?id=${laptop.id}"/>
           <button type="submit" onclick="location.href='${show_details}'">details</button>
        <img src="${pic}" height="200" width="200"/>

        <c:url var="buy_action" value="/laptops/buy"/>
        <form action="${buy_action}" method="post">
            <input type="hidden" name="item_id" value="${laptop.id}"/>
            <input type="hidden" name="action" value="addToCart">
            <button type="submit">add to cart</button>
        </form>

        <security:authorize access="hasRole('ROLE_ADMIN')">
            <c:url var="edit" value="/admin/laptops/edit?id=${laptop.id}"/>
            <button type="submit" onclick="location.href='${edit}'">edit</button>

            <c:url var="edit_photos" value="/admin/laptops/edit_photos?id=${laptop.id}"/>
            <button type="submit" onclick="location.href='${edit_photos}'">change photos</button>

            <c:url var="delete" value="/admin/laptops/delete?id=${laptop.id}"/>
            <a href="${delete}">delete item</a>

            <c:url var="add_url" value="/admin/laptops/add"/>
            <button type="submit" onclick="location.href='${add_url}'">new</button>
        </security:authorize>



    </span>
</c:forEach>

</body>
</html>
