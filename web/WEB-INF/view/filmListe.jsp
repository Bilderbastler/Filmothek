<%-- 
    Document   : clientFilmListe
    Created on : 27.06.2010, 14:08:54
    Author     : florianneumeister
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="flo" uri="/WEB-INF/tlds/flimmerkasten_tag_library.tld" %>
<c:import url="/WEB-INF/view/templates/header.jsp" />
<div class="box">
        <h1>Filme</h1>
        <c:forEach items="${films}" var="film">
            <table>
                <thead><h3>${film.name}</h3></thead>
                <tr>
                    <td width="500px"> <flo:FilmStatusTagHandler film="${film}" user="${sessionScope['user']}" /></td>
                    <td><flo:FilmAction film="${film}" user="${sessionScope['user']}" baseURL="${pageContext.request.contextPath}" /></td>
                </tr>
            </table>
        </c:forEach>
    </div>
<c:import url="/WEB-INF/view/templates/footer.jsp" />