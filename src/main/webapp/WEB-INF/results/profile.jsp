<%--
  Created by IntelliJ IDEA.
  User: pomic
  Date: 21/06/2024
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.Date" %>
<%@ page import="model.beans.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Categoria" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo Utente</title>
    <link rel="stylesheet" href="./css/styles.css">
    <link rel="stylesheet" href="./css/profileStyles.css">

</head>
<body>
<%@ include file="/WEB-INF/results/header.jsp" %>
<div class="profile-container">
    <div class="profile-header">
        <h2>Profilo Utente</h2>
    </div>
    <div class="profile-info">
        <% Utente utente=(Utente) session.getAttribute("utente");%>
        <div><strong>ID Utente:</strong> <%=utente.getIdUtente()%></div>
        <div><strong>Nome:</strong> <%=utente.getNome()%></div>
        <div><strong>Cognome:</strong> <%=utente.getCognome()%></div>
        <div><strong>Email:</strong> <%=utente.getEmail()%></div>
        <div><strong>Data di Nascita:</strong> <%=utente.getDataDiNascita()%></div>
    </div>
    <div class="profile-buttons">
        <form action="UserServlet" method="post">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="profile-button">Logout</button>
        </form>
        <form action="OrderHistoryServlet" method="get">
            <button type="submit" class="profile-button">Storico Ordini</button>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/results/footer.jsp" %>
</body>
</html>
