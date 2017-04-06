<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Login</h2>
<c:url value="/security_check" var="login_url"/>
<form name="loginform" action="${login_url}" method="post">
    <input type="text" name="username"/>
    <input type="password" name="password"/>
    <button type="submit">Log in!</button>
</form>

<p>
    <c:url value="/register" var="register_url"/>
    <a href="${register_url}">register</a>
</p>
</body>
</html>
