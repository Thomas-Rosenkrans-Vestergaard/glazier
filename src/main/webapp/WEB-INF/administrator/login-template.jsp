<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="../css/materialize.min.css"/>
        <link rel="stylesheet" href="../css/administrator.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="../js/materialize.min.js"></script>
        <title>Administrator login</title>
    </head>
    <body>
        <form id="administrator-login-form" method="POST">
            <%@ include file="../jspf/messages.jspf" %>
            <div class="input-field">
                <input id="username" type="text" name="username" class="validate" required="required">
                <label for="username">Username</label>
            </div>
            <div class="input-field">
                <input id="password" type="password" name="password" class="validate" required="required">
                <label for="password">Password</label>
            </div>   
            <button class="btn-large red waves-effect waves-light" type="submit" name="submit">Submit
                <i class="material-icons right">send</i>
            </button>
        </form>    
    </body>
</html>
