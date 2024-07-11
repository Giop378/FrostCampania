<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Categoria" %>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FrostCampania - Prodotti per Categoria</title>
    <link rel="stylesheet" href="./css/styles.css">
</head>
<body>
<%@ include file="/WEB-INF/results/header.jsp" %>

<div class="main">
    <div class="intro">
        <% Categoria categoriaScelta = (Categoria) request.getAttribute("categoriaScelta"); %>
        <h1><%= categoriaScelta.getNome() %></h1>
    </div>

    <div class="category-image">
        <img src="<%= categoriaScelta.getImmagine() %>" alt="<%= categoriaScelta.getNome() %>" class="category-img">
        <p class="category-description">
            <%= categoriaScelta.getDescrizione() %>
        </p>
    </div>

    <div class="featured-products">
        <h2>PRODOTTI</h2>
        <div class="product-list" id="lista-prodotti">
            <% List<Prodotto> prodottiPerCategoria = (List<Prodotto>) request.getAttribute("prodottiPerCategoria");
                for (Prodotto prodotto : prodottiPerCategoria) { %>
            <div class="product">
                <a href="prodotto?id=<%= prodotto.getIdProdotto() %>">
                    <img src="<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>" class="product-img">
                    <h3><%= prodotto.getNome() %></h3>
                </a>
                <p id="prezzo-iniziale">Prezzo iniziale: <%= String.format("%.2f", prodotto.getPrezzo() / (100.0 - prodotto.getSconto())).replace('.', ',') %>€</p>
                <p>Sconto: <%= prodotto.getSconto() %>%</p>
                <p>Prezzo: <%= String.format("%.2f", prodotto.getPrezzo() / 100.0).replace('.', ',') %>€</p>
                <div class="add-to-cart">
                    <form action="add-product-cart" method="post">
                        <input type="hidden" name="idProdotto" value="<%= prodotto.getIdProdotto() %>">
                        <input type="number" name="quantità" value="1" min="1" step="1" class="quantity-input">
                        <button type="submit" class="add-to-cart-button">Aggiungi al carrello</button>
                    </form>
                </div>
            </div>
            <% } %>
        </div>
    </div>

    <div class="pagination">
        <button id="pagina-precedente" onclick="cambiaPagina(-1)">Precedente</button>
        <span id="info-pagina"></span>
        <button id="pagina-successiva" onclick="cambiaPagina(1)">Successiva</button>
    </div>

    <%@ include file="/WEB-INF/results/footer.jsp" %>
    <script src="./script/paging.js"></script>
</body>
</html>

