<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Categoria" %>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FrostCampania - Prodotti per Categoria</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>
<%@ include file="/WEB-INF/results/header.jsp" %>

<div class="main">


    <div class="intro">
        <% Categoria categoriaScelta=(Categoria) request.getAttribute("categoriaScelta");%>
        <h1><%=categoriaScelta.getNome() %></h1>
    </div>



    <div class="category-image">
        <img src="<%=categoriaScelta.getImmagine()%>" alt="<%= categoriaScelta.getNome() %>" class="category-img">
        <p class="category-description">
            <%= categoriaScelta.getDescrizione()%>
        </p>
    </div>

    <div class="featured-products">
        <h2>PRODOTTI</h2>
        <div class="product-list">
            <% List<Prodotto> prodottiPerCategoria = (List<Prodotto>) request.getAttribute("prodottiPerCategoria");
                for (Prodotto prodotto : prodottiPerCategoria) { %>
            <div class="product">
                <img src="<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>" class="product-img">
                <h3><%= prodotto.getNome() %></h3>
                <p id="initial-price">Prezzo iniziale: <%= String.format("%.2f", prodotto.getPrezzo() / (100.0- prodotto.getSconto())).replace('.', ',') %>€</p>
                <p>Sconto: <%= prodotto.getSconto()%>%</p>
                <p>Prezzo scontato: <%= String.format("%.2f", prodotto.getPrezzo() / 100.0).replace('.', ',') %>€</p>
                <p>Descrizione: <%= prodotto.getDescrizione() %></p>
                <div class="add-to-cart">
                    <input type="number" value="1" min="1" class="quantity-input">
                    <button class="add-to-cart-button">Aggiungi al carrello</button>
                </div>
            </div>
            <% } %>
        </div>
    </div>

    <%@ include file="/WEB-INF/results/footer.jsp" %>
</body>
</html>


