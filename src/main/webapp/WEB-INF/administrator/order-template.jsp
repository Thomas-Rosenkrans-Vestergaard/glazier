<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="jspf/top.jspf" %>
<%@ include file="jspf/orders-menu-template.jspf" %>
<form class="main" method="POST">

    <div class="input-field col s12">
        <input id="id" type="number" class="validate" name="id" required="required" disabled="disabled" value="${order.getID()}">
        <label for="id">ID</label>
    </div>

    <fieldset class="row">
        <p>Product dimensions.</p>
        <div class="input-field col s12">
            <input id="width" type="number" class="validate" name="width" required="required" value="${order.getWidthMillimeters()}">
            <label for="width">Width (millimeters)</label>
        </div>
        <div class="input-field col s12">
            <input id="height" type="number" class="validate" name="height" required="required" value="${order.getHeightMillimeters()}">
            <label for="height">Height (millimeters)</label>
        </div>
    </fieldset>

    <select id="glass-type-select" name="glass" required="required">
        <option value="" disabled selected>Glass type.</option>
        <c:forEach items="${glasses}" var="glass">
            <option value="${glass.getID()}" ${order.getGlass().equals(glass) ? 'selected' : ''}>${glass.getName()}</option>
        </c:forEach>
    </select>

    <select id="frame-select" name="frame" required="required">
        <option value="" disabled selected>Frame type</option>
        <c:forEach items="${frames}" var="frame">
            <option value="${frame.getID()}" ${order.getFrame().equals(frame) ? 'selected' : ''}>${frame.getName()}</option>
        </c:forEach>
    </select>

    <fieldset>
        <div class="input-field col s12">
            <input id="customer_name" type="text" class="validate" name="customer_name" required="required" value="${order.getCustomerName()}">
            <label for="customer_name">Your name</label>
        </div>
        <div class="input-field col s12">
            <input id="customer_address" type="text" class="validate" name="customer_address" required="required" value="${order.getCustomerAddress()}">
            <label for="customer_address">Your address</label>
        </div>
        <div class="input-field col s12">
            <input id="customer_zip" type="text" class="validate" name="customer_zip" required="required" value="${order.getCustomerZip()}">
            <label for="customer_zip">Your zipcode</label>
        </div>
        <div class="input-field col s12">
            <input id="customer_city" type="text" class="validate" name="customer_city" required="required" value="${order.getCustomerCity()}">
            <label for="customer_city">Your city</label>
        </div>
        <div class="input-field col s12">
            <input id="customer_email" type="text" class="validate" name="customer_email" required="required" value="${order.getCustomerEmail()}">
            <label for="customer_email">Your email</label>
        </div>
        <div class="input-field col s12">
            <input id="customer_phone" type="text" class="validate" name="customer_phone" required="required" value="${order.getCustomerPhone()}">
            <label for="customer_phone">Your phone number</label>
        </div>
    </fieldset>

    <div class="input-field col s12">
        <button class="btn-large red waves-effect waves-light" type="submit" name="action" value="update">Update
            <i class="material-icons right">update</i>
        </button>
    </div>

    <div class="input-field col s12">
        <button class="btn-large red waves-effect waves-light" type="submit" name="action" value="delete">Delete
            <i class="material-icons right">delete</i>
        </button>
    </div>

    <script>
        $(document).ready(function () {
            $('#glass-type-select').material_select();
            $('#frame-select').material_select();
        });
    </script>

</form>
<%@ include file="jspf/bot.jspf" %>
