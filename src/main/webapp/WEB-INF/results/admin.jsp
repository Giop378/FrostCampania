<%--
  Created by IntelliJ IDEA.
  User: DomeA
  Date: 20/06/2024
  Time: 08:57
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
    <title>Area Amministratore</title>
    <link rel="stylesheet" href="./css/adminStyles.css">
</head>
<body>
<div class="header-admin">
    <a href="index.html" class="logo" aria-label="Home">
        <img src="./images/logo.png" alt="FrostCampania Logo" class="logo-img">
    </a>
</div>
<h1>Area Amministratore</h1>
<div class="button-container">
    <button onclick="location.href='admin-choice-servlet?choice=addproduct'">Aggiungi Prodotto</button>
    <button onclick="location.href='visualizza-ordini.jsp'">Visualizza Ordini</button>
    <button onclick="location.href='visualizza-cliente.jsp'">Visualizza Cliente</button>
    <button onclick="location.href='logout.jsp'">Esci</button>
</div>
</body>
</html>
