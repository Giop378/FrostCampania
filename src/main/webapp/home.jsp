<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Categoria" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <div class="intro">
        <h1>Benvenuto in FrostCampania</h1>
        <p>Scopri le nostre offerte esclusive sui prodotti surgelati di alta qualità.</p>
    </div>
    <div class="featured-products">
        <h2>VETRINA</h2>
        <div class="product-list">
            <c:forEach var="prodotto" items="${requestScope.prodottiVetrina}">
                <div class="product">
                    <a href="prodotto?id=${prodotto.idProdotto}">
                        <img src="${prodotto.immagine}" alt="${prodotto.nome}" class="product-img">
                        <h3>${prodotto.nome}</h3>
                    </a>
                    <p id="initial-price">Prezzo iniziale: <fmt:formatNumber value="${prodotto.prezzo / (100.0 - prodotto.sconto)}" type="currency" currencySymbol="€" maxFractionDigits="2"/></p>
                    <p>Sconto: ${prodotto.sconto}%</p>
                    <p>Prezzo: <fmt:formatNumber value="${prodotto.prezzo / 100.0}" type="currency" currencySymbol="€" maxFractionDigits="2"/></p>
                    <div class="add-to-cart">
                        <form action="add-product-cart" method="post">
                            <input type="hidden" name="idProdotto" value="${prodotto.idProdotto}">
                            <input type="number" name="quantità" value="1" min="1" step="1" class="quantity-input">
                            <button type="submit" class="add-to-cart-button">Aggiungi al carrello</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="./WEB-INF/results/footer.jsp" %>
</body>
</html>


