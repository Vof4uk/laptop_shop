<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>brand form for ${action}.</title>
</head>
<body>
<%@ include file="header.jsp"%>
<c:url value="/admin/laptops/save" var="save_url"/>
<jsp:useBean id="laptop" scope="request" class="ua.mykytenko.entities.Laptop"/>
<form method="post" action="${save_url}">
    <input type="hidden" name="id" value="${laptop.id}"/>
    <input type="hidden" name="action" value="${action}"/>
    <input type="hidden" name="old_image_path_list" value="${laptop.imagesLocations}"/>
    <p>Model
        <input title="Model" type="text" name="model" value="${laptop.model}"/>
    </p>
    <p>Description
        <input title="Description" type="text" name="description" value="${laptop.description}"/>
    </p>
    <p>Cpu frequency
        <input title="Cpu frequency" type="number" step="0.01" name="cpu_frequency" value="${laptop.cpuFrequency}"/>
    </p>
    <p>RAM
        <input title="RAM" type="number" step="0.01" name="ram" value="${laptop.ram}"/>
    </p>
    <p>Price
        <input title="price" type="number" step="0.01" name="price" value="${laptop.price}"/>
    </p>
    <p>Brand
        <select title="Brands" name="brand_id">
            <c:set var="default_brand_id" value="${laptop.brand.id}"/>
            <c:forEach items="${brands}" var="brand">
                <option ${default_brand_id eq brand.id ? "selected=\"selected\"" : ""}
                        name="${brand.shortName}" value="${brand.id}">
                        ${brand.shortName}
                </option>
            </c:forEach>
        </select>
    </p>
    <p>
        In stock:
        <input type="number" value="${laptop.stock}" name="stock"/>
    </p>
    <button type="submit">Go!</button>
    <button type="button" onclick="history.back()">back</button>
    </p>
</form>

</body>
</html>
