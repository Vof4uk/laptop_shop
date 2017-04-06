<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload photos for laptop</title>
    <c:url value="/resources/js/updatefotos.js" var="script1"/>
    <script  src="${script1}"></script>
</head>
<body>
<%@ include file="header.jsp"%>
<c:url value="/admin/laptops/save" var="save_url"/>
<jsp:useBean id="laptop" scope="request" class="ua.mykytenko.entities.Laptop"/>

<c:url var="upload_action" value="/admin/laptops/upload"/>
<form method="post" enctype="multipart/form-data" name="photos_form" action="${upload_action}">
    <input type="hidden" name="id" value="${laptop.id}" />

    <c:url var="main_foto_pic" value="/${laptop.imagesLocations[0]}"/>
    <div>Main image
        <div>
            <img src="${main_foto_pic}" height="200" width="250"/>
            <p>Change with:
                <input name="main_photo" type="file"/>
            </p>
        </div>
    </div>
    <div>Secondary photos(max 3)
        <c:forEach begin="1" end="3" var="index">
            <c:url var="foto_pic" value="/${laptop.imagesLocations[index]}"/>
            <div>
                <img src="${foto_pic}" height="200" width="250"/>
                <p>Change with:
                    <input name="new_photos" type="file"/>
                </p>
            </div>
        </c:forEach>
    </div>
    <button type="submit">Change images</button>
</form>
</body>
</html>
