<%-- Hier wird die weiterleitung an das Controller-Servlet bewirkt --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope['user'].role == 'admin'}">
        <c:redirect url="/login" />
    </c:when>
    <c:when test="${sessionScope['user'].role == 'client'}">
    </c:when>
    <c:otherwise>
         <c:redirect url="/login" />
    </c:otherwise>
</c:choose>
