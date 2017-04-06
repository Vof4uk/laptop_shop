<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>brand form for ${action}.</title>
</head>
<body>
<%@ include file="header.jsp"%>
<c:url value="/admin/brands/save" var="save_url"/>
<jsp:useBean id="brand" scope="request" class="ua.mykytenko.entities.Brand"/>
<form method="post" action="${save_url}">
    <input type="hidden" name="id" value="${brand.id}"/>
    <input type="hidden" name="action" value="${action}"/>
    <p>Short name
        <input title="Short name" type="text" name="short_name" value="${brand.shortName}"/>
    </p>
    <p>Full name
    <input title="Full name" type="text" name="full_name" value="${brand.fullName}"/>
    </p>
    <p>Description
    <input title="Description" type="text" name="description" value="${brand.description}"/>
    </p>
    </p>
    <button type="submit">Go!</button>
    <button type="button" onclick="history.back()">back</button>
    </p>
</form>
</body>
</html>
