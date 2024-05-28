<%--
  Created by IntelliJ IDEA.
  User: DomeA
  Date: 28/05/2024
  Time: 08:26
  To change this template use File | Settings | File Templates.
--%>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FrostCampania - Prodotti Surgelati di Qualità</title>
    <link rel="stylesheet" href="./css/styles.css">
</head>
<body>
<%@ include file="./WEB-INF/results/header.jsp" %>

<div class="main">
    <div class="nav-items">
        <a href="#" aria-label="Pesce">PESCE</a>
        <a href="#" aria-label="Piatti Pronti">PIATTI PRONTI</a>
        <a href="#" aria-label="Gelati">GELATI</a>
        <a href="#" aria-label="Verdure">VERDURE</a>
        <a href="#" aria-label="Offerte">OFFERTE</a>
    </div>
    <div class="intro">
        <h1>Benvenuto in FrostCampania</h1>
        <p>Scopri le nostre offerte esclusive sui prodotti surgelati di alta qualità.</p>
    </div>
    <div class="featured-products">
        <h2>VETRINA</h2>
        <div class="product-list">
            <div class="product">
                <img src="#" alt="Prodotto 1" class="product-img">
                <h3>Prodotto 1</h3>
                <p>Descrizione breve del prodotto 1.</p>
            </div>
            <div class="product">
                <img src="#" alt="Prodotto 2" class="product-img">
                <h3>Prodotto 2</h3>
                <p>Descrizione breve del prodotto 2.</p>
            </div>
            <div class="product">
                <img src="#" alt="Prodotto 3" class="product-img">
                <h3>Prodotto 3</h3>
                <p>Descrizione breve del prodotto 3.</p>
            </div>
            <div class="product">
                <img src="#" alt="Prodotto 4" class="product-img">
                <h3>Prodotto 4</h3>
                <p>Descrizione breve del prodotto 4.</p>
            </div>
        </div>
    </div>
</div>
<%@ include file="./WEB-INF/results/footer.jsp" %>
</body>
</html>
