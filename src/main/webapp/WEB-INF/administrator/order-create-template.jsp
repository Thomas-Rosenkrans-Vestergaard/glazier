<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="jspf/top.jspf" %>
<%@ include file="jspf/orders-menu-template.jspf" %>
<div id="container">
    <form class="main" method="POST">

        <fieldset class="row">
            <p>Chose the messurement to use when specifying the dimensions of the product.</p>
            <p>
                <input name="messurement" type="radio" id="messurement-mm" value="mm">
                <label for="messurement-mm">Millimeters</label>
            </p>
            <p>
                <input checked="checked" name="messurement" type="radio" id="messurement-cm" value="cm">
                <label for="messurement-cm">Centimeters</label>
            </p>
            <p>
                <input class="with-gap" name="messurement" type="radio" id="messurement-m" value="m">
                <label for="messurement-m">Meters</label>
            </p>
        </fieldset>

        <fieldset class="row">
            <p>Product dimensions.</p>
            <div class="input-field col s12">
                <input id="width" type="number" class="validate" name="width" required="required">
                <label for="width">Width</label>
            </div>
            <div class="input-field col s12">
                <input id="height" type="number" class="validate" name="height" required="required">
                <label for="height">Height</label>
            </div>
        </fieldset>

        <select id="glass-type-select" name="glass" required="required">
            <option value="" disabled selected>Glass type.</option>
            <c:forEach items="${glasses}" var="glass">
                <option value="${glass.getID()}">${glass.getName()}</option>
            </c:forEach>
        </select>

        <select id="frame-select" name="frame" required="required">
            <option value="" disabled selected>Frame type</option>
            <c:forEach items="${frames}" var="frame">
                <option value="${frame.getID()}">${frame.getName()}</option>
            </c:forEach>
        </select>

        <fieldset>
            <div class="input-field col s12">
                <input id="customer_name" type="text" class="validate" name="customer_name" required="required" >
                <label for="customer_name">Your name</label>
            </div>
            <div class="input-field col s12">
                <input id="customer_address" type="text" class="validate" name="customer_address" required="required">
                <label for="customer_address">Your address</label>
            </div>
            <div class="input-field col s12">
                <input id="customer_zip" type="text" class="validate" name="customer_zip" required="required">
                <label for="customer_zip">Your zipcode</label>
            </div>
            <div class="input-field col s12">
                <input id="customer_city" type="text" class="validate" name="customer_city" required="required">
                <label for="customer_city">Your city</label>
            </div>
            <div class="input-field col s12">
                <input id="customer_email" type="text" class="validate" name="customer_email" required="required">
                <label for="customer_email">Your email</label>
            </div>
            <div class="input-field col s12">
                <input id="customer_phone" type="text" class="validate" name="customer_phone" required="required">
                <label for="customer_phone">Your phone number</label>
            </div>
        </fieldset>

        <div class="input-field col s12">
            <button class="btn-large red waves-effect waves-light" type="submit" name="action" value="create">Create
                <i class="material-icons right">update</i>
            </button>
        </div>

        <script>
            $(document).ready(function () {
                $('#glass-type-select').material_select();
                $('#frame-select').material_select();
            });
        </script>

    </form>
</div>
<%@ include file="jspf/bot.jspf" %>
