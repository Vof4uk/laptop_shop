<%--
  Created by IntelliJ IDEA.
  User: Mykytenko
  Date: 28.03.2017
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<%@include file="header.jsp" %>
Fill in the form:
<c:url value="/register" var="reg_url"/>
<form action="${reg_url}" method="post" name="user_form_reg">
        Name: <input type="text" name="username" title="username"/>
        Password:<input type="password" name="password" title="password"/>
        <input type="submit" value="go">
</form>

</body>
</html>
