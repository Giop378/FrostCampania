<%--
  Created by IntelliJ IDEA.
  User: DomeA
  Date: 28/05/2024
  Time: 06:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <button class="search-button" aria-label="Search Button">Cerca</button>
  </div>

  <div class="icon-container">
    <a href="utente-servlet" class="icon-access" aria-label="Accedi">
      <img src="./images/login.png" alt="Accedi" class="icon-img">
    </a>
    <a href="#" class="icon-cart" aria-label="Carrello">
      <img src="./images/carrello.png" alt="Carrello" class="icon-img">
    </a>
  </div>
</div>
<div class="nav-items">
  <% List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
    for (Categoria categoria : categorie) { %>
  <a href="categoria?categoria=<%=categoria.getNome()%>" aria-label="<%=categoria.getNome()%>" value="<%=categoria.getNome()%>"><%=categoria.getNome()%></a>
  <% } %>
</div>

</body>
</html>
