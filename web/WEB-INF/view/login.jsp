<%--
    Document   : login
    Created on : 24.06.2010, 20:56:03
    Author     : neumeister
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="flo" uri="/WEB-INF/tlds/flimmerkasten_tag_library.tld" %>
<c:import url="/WEB-INF/view/templates/header.jsp" />
<div class="box login">
    <h1>Login-Box</h1>
    <flo:loginForm />
</div>

<c:import url="/WEB-INF/view/templates/footer.jsp" />
