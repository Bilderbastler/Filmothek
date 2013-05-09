<%-- 
    Document   : movieListTag
    Created on : 07.07.2010, 21:23:25
    Author     : florianneumeister
--%>
<%-- dieser Tag wird nicht weiter entwickelt --%>
<%@tag description="erzeugt eine Auflistung aller Filme" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="films" required="true"  %>

<%-- any content can be specified here e.g.: --%>
<ul>
<c:forEach var="film" items="films" >
    <li>${film.getName}</li>
</c:forEach>
</ul>