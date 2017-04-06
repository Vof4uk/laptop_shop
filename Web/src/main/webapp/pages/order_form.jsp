<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Finish order registration</title>
</head>
<body>
<%@include file="header.jsp" %>
<h3>Please fill in address and bank-account id fields:</h3>
<c:url var="finish_payment_url" value="/laptops/shopping_cart/pay"/>
<form method="post" action="${finish_payment_url}">
    <p>
        Address
        <input type="text" name="address" title="Address">
    </p>
    <p>
        Bank account id
    <input type="number" name="bank_account_id" title="Bank ID">
    </p>


    <p>Look again at your order:</p>
    <table>
        <tr>
            <th>Model</th>
            <th>Price</th>
            <th></th>
            <th>qty.</th>
            <th></th>
            <th>cost</th>
        </tr>
        <c:forEach items="${order.orderPositions}" var="position">
            <tr>
                <td>${position.laptop.model}</td>
                <td>${position.laptop.price}</td>
                <th>X</th>
                <td>${position.quantity}</td>
                <th>=</th>
                <td>${position.quantity * position.laptop.price}</td>
            </tr>
        </c:forEach>
    </table>
    <h3>Total: ${order.totalCost}</h3>
    <button onclick="history.back()">cancel</button>
    <button type="submit">pay</button>
</form>
</body>
</html>
