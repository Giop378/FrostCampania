<%@ page import="model.beans.Categoria" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: pomic
  Date: 22/06/2024
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Modifica del prodotto</title>
  <link rel="stylesheet" href="./css/modifyDeleteProductStyles.css">
  <script src="./script/modifyDelete.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="header">
  <a href="user-servlet" class="logo" aria-label="Home">
    <img src="./images/logo.png" alt="FrostCampania Logo" class="logo-img">
  </a>
  <div class="spacer"></div>
  <div class="search-container">
    <input type="text" placeholder="Cerca i nostri prodotti..." class="search-bar" aria-label="Search">
    <div class="search-results"></div>
  </div>
</div>

<div id="intro">
  <h1>Modifica prodotto</h1>
  <h3>Seleziona un prodotto dalla barra di ricerca per modificarlo</h3>
</div>
<form action="modify-product-servlet" method="post">
  <input type="hidden" id="id" name="id" value="0">

  <label for="nome">Nome:</label>
  <input type="text" id="nome" name="nome" required><br>

  <label for="descrizione">Descrizione:</label>
  <input type="text" id="descrizione" name="descrizione" required><br>

  <label for="prezzo">Prezzo(in centesimi):</label>
  <input type="number" id="prezzo" name="prezzo" required><br>

  <label for="vetrina">In Vetrina:</label>
  <input type="checkbox" id="vetrina" name="vetrina" value="true"><br>

  <label for="sconto">Sconto(percentuale intera):</label>
  <input type="number" id="sconto" name="sconto" value="0"><br>

  <label for="quantita">Quantità:</label>
  <input type="number" id="quantita" name="quantita" required  min="1" step="1"><br>

  <label for="categoria">Categoria:</label>
  <select id="categoria" name="nomecategoria" required>
    <% List<Categoria> categorie = (List<Categoria>) application.getAttribute("categorie");
      for (Categoria categoria : categorie) { %>
    <option  value="<%=categoria.getNome()%>"><%=categoria.getNome()%></option>
    <% } %>
  </select><br>

  <input type="submit" value="Invia">
</form>
</body>
</html>
