<%-- 
    Document   : register
    Created on : 27.06.2010, 21:25:36
    Author     : neumeister
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/view/templates/header.jsp" />
<div class="box register">
    <h1>Registrierung</h1>
    <c:choose>
        <c:when test="${registerStatus == 'mail send'}" >
            <p>Eine Mail zur Aktivierung deines Accounts wurde an deine Adresse geschickt</p>
            <p>Zurück zur <a href="<c:url value="/login" />">Login-Seite</a></p>
        </c:when>
        <c:otherwise>
            <form action="/~j81/register" method="post" >
                <fieldset>
                    <p>
                        <label for="name">Name</label>
                        <input type="text" name="name" size="30" value="${name}" />
                    </p>
                    <p>
                        <label for="email">E-Mail</label>
                        <input type="text" name="email" size="30" value="${email}"/>
                    </p>
                    <p><span class="important">Nur E-Mail Adressen der HAW angeben</span><br /></p>
                </fieldset>
                <fieldset>
                    <p>
                        <label for="password">Passwort</label>
                        <input type="password" name="password" size="30"/>
                    </p>
                    <p>
                        <label for="password2">Passwort wiederholen</label>
                        <input type="password" name="password2" size="30" />
                    </p>
                </fieldset>
                <p>
                    <input type="submit" value="Abschicken" />
                </p>
                    <ul class="error">
                        <c:forEach items="${errors}" var="error" >
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<c:import url="/WEB-INF/view/templates/footer.jsp" />
