<%--
    Document   : loginTag
    Created on : 24.06.2010, 22:21:16
    Author     : florianneumeister
--%>
<%@tag description="Erzeugt das Login-Formular" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="model.UserBean" scope="session" />
<c:choose>
    <%-- Wenn der Benutzer noch nicht eingeloggt ist, zeige das Login Formular --%>
    <c:when test="${sessionScope['loggedIn'] != 'true'}" >
        <form class="login" action="<c:url value="/login" />" method="post">
                <c:out value="<p class='error'>${loginError}</p>" escapeXml="false" default=""/>
            <p>
                <label for="email" >E-Mail</label>
                <input type="text" name="email" size="30" value="${email}"/>
            </p>
            <p>
                <label for="password" >Passwort</label>
                <input type="password" size="30" name="password"/>
            </p>
            <p>

                <input type="submit" value="anmelden" />
            </p>
            <p>
                <a href="<c:url value="/register" />" id="register_link">Account erstellen</a>
            </p>
        </form>
    </c:when>
    <%-- Ist der Benutzer eingeloggt, gebe ihm die Möglichkeit, sich auszuloggen --%>
    <c:otherwise >
        <form class="login" action="<c:url value="/logout" />" method="post">
            <p>
                Du bist angemeldet als <em>${sessionScope['user'].name}</em>
            </p>
            <p>
                Zur <a href="<c:url value="/clientFilmListe" />">Filmauswahl</a> zurück
                <input type="submit" value="abmelden" />
            </p>
        </form>
    </c:otherwise>
</c:choose>
    <%-- mögliche Feher abfangen: Passwort oder Mail falsch --%>