<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="jspf/top.jspf" %>
<%@ include file="jspf/frames-menu-template.jspf" %>
<form method="POST" class="main">

    <div class="input-field col s12">
        <input id="id" type="number" class="validate" name="id" required="required" disabled="disabled" value="${frame.getID()}">
        <label for="id">ID</label>
    </div>

    <div class="input-field col s12">
        <input id="name" type="text" class="validate" name="name" required="required" value="${frame.getName()}">
        <label for="name">Name</label>
    </div>

    <div class="input-field col s12">
        <textarea id="description" class="materialize-textarea" class="validate" name="description" required="required">${frame.getDescription()}</textarea>
        <label for="description">Description</label>
    </div>

    <div class="input-field col s12">
        <input id="price_per_meter" type="number" class="validate" name="price_per_meter" required="required" value="${frame.getPricePerMeter()}"/>
        <label for="price_per_meter">Price per meter</label>
    </div>

    <div class="input-field col s12">
        <button class="btn-large red waves-effect waves-light" type="submit" name="action" value="update">Update
            <i class="material-icons right">update</i>
        </button>
        <button class="btn-large red waves-effect waves-light" type="submit" name="action" value="delete">Delete
            <i class="material-icons right">delete</i>
        </button>
    </div>

</form>
<%@ include file="jspf/bot.jspf" %>
