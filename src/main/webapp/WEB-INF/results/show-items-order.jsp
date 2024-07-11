<%--
  Created by IntelliJ IDEA.
  User: pomic
  Date: 06/07/2024
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Prodotto" %>
<%@ page import="model.beans.ItemOrdine" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dettagli Ordine</title>
    <link rel="stylesheet" href="./css/showItemsOrderStyles.css">
</head>
<body>
<%@include file="header.jsp"%>
<div class="content-wrapper">
<div class="main">
    <h1>Dettagli Ordine</h1>
    <% List<ItemOrdine> itemOrdineList = (List<ItemOrdine>) request.getAttribute("itemOrdineList");
        for (ItemOrdine itemOrdine : itemOrdineList) { %>
    <div class="item-card">
        <img src="<%= itemOrdine.getImmagine() %>" alt="Immagine Prodotto">
        <div class="item-details">
            <h2><%= itemOrdine.getNome() %></h2>
            <p>ID: <%= itemOrdine.getIdItemOrdine()%></p>
            <p id="initial-price">Prezzo iniziale: <%= String.format("%.2f", itemOrdine.getPrezzo() / (100.0- itemOrdine.getSconto())).replace('.', ',') %>€</p>
            <p>Sconto: <%= itemOrdine.getSconto()%>%</p>
            <p>Prezzo: <%= String.format("%.2f", itemOrdine.getPrezzo() / 100.0).replace('.', ',') %>€</p>
            <p>Quantità: <%= itemOrdine.getQuantità() %></p>
        </div>
    </div>
    <% } %>
</div>
    <div id="spacer"></div>
</div>

<%@include file="footer.jsp"%>

</body>
</html>
