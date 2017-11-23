<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="jspf/top.jspf" %>
<%@ include file="jspf/glasses-menu-template.jspf" %>
<table class="striped">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price per meter<sup>2</sup></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${glasses}" var="glass">
            <tr>
                <td><a href="glasses?id=${glass.getID()}">${glass.getID()}</a></td>
                <td>${glass.getName()}</td>
                <td>${glass.getDescription()}</td>
                <td>${glass.getPricePerSquareMeter()}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<%@ include file="jspf/bot.jspf" %>
<%@ include file="jspf/bot.jspf" %>