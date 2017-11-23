<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="jspf/top.jspf" %>
<div id="container">
    <h2>Order product</h2>
    <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus</p>
    <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, commodo vitae, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. Donec non enim in turpis pulvinar facilisis. Ut felis. Praesent dapibus, neque id cursus faucibus, tortor neque egestas augue, eu vulputate magna eros eu erat. Aliquam erat volutpat. Nam dui mi, tincidunt quis, accumsan porttitor, facilisis luctus, metus</p>

    <h2>Product details</h2>

    <form id="order-product-form" method="POST">


        <fieldset class="row">
            <p>Chose the messurement to use when specifying the dimensions of the product.</p>
            <p>
                <input name="messurement" type="radio" id="messurement-mm" value="mm" ${fillMessurement == 'mm' ? 'checked' : ''}>
                <label for="messurement-mm">Millimeters</label>
            </p>
            <p>
                <input checked="checked" name="messurement" type="radio" id="messurement-cm" value="cm" ${fillMessurement == 'cm' ? 'checked' : ''}>
                <label for="messurement-cm">Centimeters</label>
            </p>
            <p>
                <input class="with-gap" name="messurement" type="radio" id="messurement-m" value="m" ${fillMessurement == 'm' ? 'checked' : ''}>
                <label for="messurement-m">Meters</label>
            </p>
        </fieldset>
        <fieldset class="row">
            <p>Enter the dimensions of the product.</p>
            <div class="input-field col s12">
                <input id="width" type="number" class="validate" name="width" required="required" value="${fillWidth}">
                <label for="width">Width</label>
            </div>
            <div class="input-field col s12">
                <input id="height" type="number" class="validate" name="height" required="required" value="${fillHeight}">
                <label for="height">Height</label>
            </div>
        </fieldset>

        <select id="glass-type-select" name="glass" required="required">
            <option value="" disabled selected>Chose the type of glass to use on the product.</option>
            <c:forEach items="${glasses}" var="glass">
                <option value="${glass.getID()}" ${fillGlass == glass.getID() ? 'selected' : ''}>${glass.getName()}</option>
            </c:forEach>
        </select>

        <select id="frame-select" name="frame" required="required">
            <option value="" disabled selected>Chose the frame to use on the product</option>
            <c:forEach items="${frames}" var="frame">
                <option value="${frame.getID()}" ${fillFrame == frame.getID() ? 'selected' : ''}>${frame.getName()}</option>
            </c:forEach>
        </select>

        <fieldset>
            <div class="input-field col s12">
                <input id="customer_name" type="text" class="validate" name="customer_name" required="required">
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
            <button class="btn-large red waves-effect waves-light" type="submit" name="submit">Order
                <i class="material-icons right">send</i>
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