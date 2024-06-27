<%--
  Created by IntelliJ IDEA.
  User: pomic
  Date: 19/06/2024
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Categoria" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FrostCampania - Prodotti Surgelati di Qualità</title>
    <link rel="stylesheet" href="./css/loginStyles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="login-container">
    <div class="login-box">
        <h2>Registrati</h2>
        <form action="login-servlet?action=register" method="post">
            <div class="input-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" required>
            </div>
            <div class="input-group">
                <label for="cognome">Cognome</label>
                <input type="text" id="cognome" name="cognome" required>
            </div>
            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}">
            </div>
            <div class="input-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="input-group">
                <label for="datadinascita">Data di Nascita</label>
                <input type="date" id="datadinascita" name="datadinascita" required>
            </div>
            <button type="submit" class="login-button">Register</button>
        </form>
        <p id="error-message" class="error-message"></p>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
