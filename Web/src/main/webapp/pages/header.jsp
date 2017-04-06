<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <table>
        <tr>
            <th>
                <c:url value="/home" var="home_url"/>
                <%--<a href="${laptops_url}">| laptops</a>--%>
                <button onclick="location.href='${home_url}'">home</button>
            </th>
            <th>
                <c:url value="/laptops/all" var="laptops_url"/>
                <%--<a href="${laptops_url}">| laptops</a>--%>
                <button onclick="location.href='${laptops_url}'">laptops</button>
            </th>
            <th></th>
            <c:url value="/laptops/shopping_cart" var="cart_url"/>
            <jsp:useBean id="shoppingCart" class="ua.mykytenko.web.impl.ShoppingCartImpl" scope="session"/>
            <th>
                <button onclick="location.href='${cart_url}'">shopping cart(${shoppingCart.itemsCount} items)</button>
                <%--<a href="${cart_url}">| shopping cart(${shoppingCart.itemsCount} items) </a>--%>
            </th>
            <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
                <c:url value="/logout" var="logout"/>
                <th>
                <button onclick="location.href='${logout}'">logout</button>
                    <%--<a href="${logout}">| log out |</a>--%>
                </th>
            </security:authorize>
        </tr>
    </table>
</header>
