<%--
  Created by IntelliJ IDEA.
  User: DomeA
  Date: 20/06/2024
  Time: 11:51
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
    <title>Dettaglio Prodotto</title>
    <link rel="stylesheet" href="./css/productStyles.css">
</head>
<body>
<%@ include file="/WEB-INF/results/header.jsp" %>

<div class="main">
    <div class="product-detail">
        <% Prodotto prodotto = (Prodotto) request.getAttribute("prodotto"); %>
        <img src="<%= prodotto.getImmagine() %>" alt="<%= prodotto.getNome() %>" class="product-img">
        <div class="product-info">
            <h1><%= prodotto.getNome() %></h1>
            <p>ID prodotto: <%= prodotto.getIdProdotto()%></p>
            <p id="initial-price">Prezzo iniziale: <%= String.format("%.2f", prodotto.getPrezzo() / (100.0 - prodotto.getSconto())).replace('.', ',') %>€</p>
            <p>Sconto: <%= prodotto.getSconto()%>%</p>
            <p>Prezzo: <%= String.format("%.2f", prodotto.getPrezzo() / 100.0).replace('.', ',') %>€</p>
            <p>Descrizione: <%= prodotto.getDescrizione() %></p>
            <div class="add-to-cart">
                <form action="add-product-cart" method="post">
                    <input type="hidden" name="idProdotto" value="<%= prodotto.getIdProdotto() %>">
                    <input type="number" name="quantità" value="1" min="1"  step="1" class="quantity-input">
                    <button type="submit" class="add-to-cart-button">Aggiungi al carrello</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/results/footer.jsp" %>
</body>
</html>




