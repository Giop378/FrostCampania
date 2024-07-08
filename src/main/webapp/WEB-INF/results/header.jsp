<%--
  Created by IntelliJ IDEA.
  User: DomeA
  Date: 28/05/2024
  Time: 06:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Categoria" %>
<!DOCTYPE html>
<html>
<head>
  <title>header</title>
  <link rel="stylesheet" href="./css/header.css">
</head>
<body>
<div class="header">
  <a href="index.html" class="logo" aria-label="Home">
    <img src="./images/logo.png" alt="FrostCampania Logo" class="logo-img">
  </a>
  <div class="search-container">
    <input type="text" placeholder="Cerca i nostri prodotti..." class="search-bar" aria-label="Search">
    <div class="search-results"></div>
  </div>


  <div class="icon-container">
    <a href="user-servlet" class="icon-access" aria-label="Accedi">
      <img src="./images/login.png" alt="Accedi" class="icon-img">
    </a>
    <a href="carrello" class="icon-cart" aria-label="Carrello">
      <img src="./images/carrello.png" alt="Carrello" class="icon-img">
    </a>
  </div>
</div>
<div class="nav-items">
  <% List<Categoria> categorie = (List<Categoria>) application.getAttribute("categorie");
    for (Categoria categoria : categorie) { %>
  <a href="categoria?categoria=<%=categoria.getNome()%>" aria-label="<%=categoria.getNome()%>"
     value="<%=categoria.getNome()%>"><%=categoria.getNome()%></a>
  <% } %>
</div>
<script src="./script/header.js"></script>
</body>
</html>