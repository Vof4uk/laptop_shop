<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<body>
<%@ include file="header.jsp" %>
<h2>Welcome to laptop shop!</h2>
<a>
    <c:url value="/login" var="login"/>
    <a href="${login}"><h3>login</h3></a>
</a>
<p>
    <c:url value="/register" var="register_url"/>
    <a href="${register_url}">register</a>
</p>

<p>
    <spring:url value="/r" var="restHome"/>
    <a href="${restHome}">rest home</a>
</p>
</body>
</html>

