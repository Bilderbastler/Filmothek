<%--
    Document   : header
    Created on : 24.06.2010, 21:17:37
    Author     : florianneumeister
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <!-- CSS Style -->
        <link href="style/style.css" rel="stylesheet" type="text/css" media="screen" />

        <!-- JS Skripte -->
        <script type="text/javascript" src="http://www.google.com/jsapi"></script>
        <script type="text/javascript">google.load("jquery", "1.4.0");</script>

        <title>Filmothek</title>
    </head>
    <body>
    <div id="wrapper">
        <div class="center" <h1>'Die Filmothek'</h1>
            <p>am Departement Medientechnik der HAW Hamburg</p>
        </div>
        <c:if test="${sessionScope['loggedIn'] eq 'true'}">
            <div id="user_container">
                Du bist angmeldet als <a class="underline" href="<c:url value="/login"/>" >${sessionScope['user'].name}</a>
            </div>
        </c:if>
