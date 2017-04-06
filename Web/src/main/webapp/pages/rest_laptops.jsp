<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Laptops</title>
    <spring:url value="/resources/css/laptops.css" var="css_url"/>
    <link href="${css_url}" rel="stylesheet">
    <spring:url var="script2" value="/resources/js/jquery-3.2.0.min.js"/>
    <script src="${script2}"></script>
    <spring:url var="script" value="/resources/js/laptops.js"/>
    <script src="${script}"></script>
</head>
<%@include file="header.jsp" %>
<body>
<spring:url value="/r/laptops/all" var="get_laptops_all"/>
<button id="refresh_button" value="${get_laptops_all}">refresh all</button>

<spring:url value="/r/laptops/id/3" var="get_laptop_by_id"/>
<button id="get_button" value="${get_laptop_by_id}">get id=3</button>

<div id="laptops_container" class="container" myprop="/r/laptops/id/">
    <div>epty div</div>
    <div>epty div</div>
    <div>epty div</div>
</div>

</body>
</html>
