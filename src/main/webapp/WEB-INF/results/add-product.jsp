<%--
  Created by IntelliJ IDEA.
  User: pomic
  Date: 22/06/2024
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Upload del prodotto</title>
    <link rel="stylesheet" href="./css/addProductStyles.css">
</head>
<body>
<div class="header-admin">
    <a href="index.html" class="logo" aria-label="Home">
        <img src="./images/logo.png" alt="FrostCampania Logo" class="logo-img">
    </a>
</div>
<form action="add-product-servlet" method="post" enctype="multipart/form-data">
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
    <input type="number" id="quantita" name="quantita" required><br>

    <label for="nomecategoria">Nome Categoria:</label>
    <input type="text" id="nomecategoria" name="nomecategoria" required><br>

    <label for="file">File da caricare:</label>
    <input type="file" id="file" name="file" required><br>

    <input type="submit" value="Invia">
</form>
</body>
</html>