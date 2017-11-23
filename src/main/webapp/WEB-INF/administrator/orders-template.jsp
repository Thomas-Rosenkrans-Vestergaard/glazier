<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="jspf/top.jspf" %>
<%@ include file="jspf/orders-menu-template.jspf" %>
<table class="striped">
    <thead>
        <tr>
            <th>Order ID</th>
            <th>Order width (mm)</th>
            <th>Order height (mm)</th>
            <th>Order frame</th>
            <th>Order glass</th>
            <th>Customer name</th>
            <th>Customer address</th>
            <th>Customer zip</th>
            <th>Customer city</th>
            <th>Customer email</th>
            <th>Customer phone</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><a href="orders?id=${order.getID()}">${order.getID()}</a></td>
                <td>${order.getWidthMillimeters()}</td>
                <td>${order.getHeightMillimeters()}</td>
                <td><a href="frames?id=${order.getFrame().getID()}">${order.getFrame().getName()}</td>
                <td><a href="glasses?id=${order.getGlass().getID()}">${order.getGlass().getName()}</td>
                <td>${order.getCustomerName()}</td>
                <td>${order.getCustomerAddress()}</td>
                <td>${order.getCustomerZip()}</td>
                <td>${order.getCustomerCity()}</td>
                <td>${order.getCustomerEmail()}</td>
                <td>${order.getCustomerPhone()}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<%@ include file="jspf/bot.jspf" %>