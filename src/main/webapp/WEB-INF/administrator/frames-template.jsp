<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="jspf/top.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="jspf/top.jspf" %>
<table class="striped responsive-table highlight">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th class="limit-column">Description</th>
            <th>Price per meter</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${frames}" var="frame">
        <tr>
            <td><a href="frames?id=${frame.getID()}">${frame.getID()}</a></td>
            <td>${frame.getName()}</td>
            <td class="limit-column">${frame.getDescription()}</td>
            <td>${frame.getPricePerMeter()}</td>
        </tr>
    </c:forEach>
</tbody>
</table>
<%@ include file="jspf/bot.jspf" %>
<%@ include file="jspf/bot.jspf" %>
