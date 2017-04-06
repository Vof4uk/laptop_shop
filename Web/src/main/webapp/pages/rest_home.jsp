<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<c:url var="laptops_url" value="/r/laptops"/>
<a href="${laptops_url}">rest laptop page</a>
</body>
</html>
