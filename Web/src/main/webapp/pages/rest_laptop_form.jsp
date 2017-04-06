<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mykytenko
  Date: 01.04.2017
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Laptop form</title>
    <spring:url value="/resources/css/laptop-edit-form.css" var="css_url"/>
    <link href="${css_url}" rel="stylesheet">
    <spring:url var="script2" value="/resources/js/jquery-3.2.0.min.js"/>
    <script src="${script2}"></script>
    <spring:url var="script" value="/resources/js/laptop_form.js"/>
    <script src="${script}"></script>
</head>
<body>
<%@include file="header.jsp"%>
<var hidden id="id">${id}</var>
<spring:url value="/r/laptops/id/${id}" var="laptop_get_url"/>
<var hidden id="get_url">${laptop_get_url}</var>
<spring:url value="/r/admin/upload/photo" var="upload_url"/>
<var hidden id="upload_url">${upload_url}</var>
<spring:url value="/r/admin/laptop/update" var="update_url"/>
<var hidden id="update_url">${update_url}</var>
<spring:url value="/r/admin/brands" var="brands_url"/>
<var hidden id="brands_url">${brands_url}</var>


<div id="laptop_div">

</div>

</body>
</html>
