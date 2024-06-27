<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Categoria" %>
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
            <% List<Prodotto> prodottiVetrina = (List<Prodotto>) request.getAttribute("prodottiVetrina");
                for (Prodotto prodotto : prodottiVetrina) { %>
            <div class="product">
                <a href="prodotto?id=<%= prodotto.getIdProdotto() %>">
                    <img src="<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>" class="product-img">
                    <h3><%=prodotto.getNome()%></h3>
                </a>

                <p id="initial-price">Prezzo iniziale: <%= String.format("%.2f", prodotto.getPrezzo() / (100.0- prodotto.getSconto())).replace('.', ',') %>€</p>
                <p>Sconto: <%= prodotto.getSconto()%>%</p>
                <p>Prezzo: <%= String.format("%.2f", prodotto.getPrezzo() / 100.0).replace('.', ',') %>€</p>
                <div class="add-to-cart">
                    <input type="number" value="1" min="1" class="quantity-input">
                    <button class="add-to-cart-button">Aggiungi al carrello</button>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</div>
<%@ include file="./WEB-INF/results/footer.jsp" %>
</body>
</html>
