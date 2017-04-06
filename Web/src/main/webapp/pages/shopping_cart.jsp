<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
    <c:url value="/resources/js/sh_cart.js" var="script1"/>
    <script  src="${script1}"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Hello, ${shoppingCart.account eq null ? "anonimus" : shoppingCart.account.name}</h2>
<h3>Your order:</h3>
<table>
    <tr>
        <th>model</th>
        <th>price</th>
        <th>amount</th>
        <th>sum</th>
    </tr>
    <c:forEach items="${shoppingCart.orderList.entrySet()}" var="entry">
        <jsp:useBean id="entry" type="java.util.Map.Entry<ua.mykytenko.entities.Laptop,java.lang.Integer>"/>
        <tr>
            <td>${entry.key.model}</td>
            <td>${entry.key.price}</td>
            <td>${entry.value}</td>
            <td>${entry.value * entry.key.price}</td>

            <c:url value="/laptops/shopping_cart/plus?id=${entry.key.id}" var="url_plus1"/>
            <td>
                <button onclick="location.href='${url_plus1}'">+</button>
                <%--<a href="${url_plus1}">+1</a>--%>
            </td>

            <c:url value="/laptops/shopping_cart/minus?id=${entry.key.id}" var="url_minus1"/>
            <td>
                <button onclick="location.href='${url_minus1}'">-</button>
                    <%--<a href="${url_minus1}">-1</a>--%>
            </td>

            <c:url value="/laptops/shopping_cart/minusAll?id=${entry.key.id}" var="url_minus_all"/>
            <td>
                <button onclick="location.href='${url_minus_all}'">X</button>
                <%--<a href="${url_minus_all}">delete</a>--%>
            </td>
        </tr>
    </c:forEach>
</table>
<c:url var="confirm_url" value="/laptops/shopping_cart/confirm"/>
<c:if test="${shoppingCart.ready}">
    <button onclick="location.href='${confirm_url}'">checkout cart</button>
    <%--<a href="${confirm_url}">buy all!</a>--%>
</c:if>
</body>
</html>
