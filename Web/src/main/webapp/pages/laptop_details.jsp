<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Laptop ${laptop.model}</title>
</head>
<body>
<%@ include file="header.jsp"%>
<table>
    <tr>
        <td>brand</td>
        <td>${laptop.brand.shortName}</td>
    </tr>
    <tr>
        <td>model</td>
        <td>${laptop.model}</td>
    </tr>
    <tr>
        <td>pics</td>
        <c:forEach items="${laptop.imagesLocations}" var="location">
            <c:url var="location_url" value="/${location}"/>
            <td><img src="${location_url}" height="100" width="130"/></td>
        </c:forEach>
    </tr>
    <tr>
        <td>description</td>
        <td>${laptop.description}</td>
    </tr>
    <tr>
        <td>cpu</td>
        <td>${laptop.cpuFrequency}</td>
    </tr>
    <tr>
        <td>ram</td>
        <td>${laptop.ram}</td>
    </tr>
</table>
<c:url var="buy_action" value="/laptops/buy"/>
<form method="get" action="${buy_action}">
    <input type="hidden" name="item_id" value="${laptop.id}"/>
    <button type="submit">buy!</button>
</form>
</body>
</html>
