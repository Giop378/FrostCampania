<%--
  Created by IntelliJ IDEA.
  User: pomic
  Date: 06/07/2024
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Ordine" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Storico Ordini</title>
    <link rel="stylesheet" href="./css/showOrdersStyles.css">
</head>
<body>
<%@include file="header.jsp"%>

<div class="main">
    <h1>Storico Ordini</h1>
    <% List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
        for(Ordine ordine : ordini) { %>
    <div class="order-card" id="order<%= ordine.getIdOrdine() %>">
        <h2>ID Ordine: <%= ordine.getIdOrdine() %></h2>
        <p>Nome: <%= ordine.getNome() %> <%= ordine.getCognome() %></p>
        <p>Indirizzo: <%= ordine.getVia() %>, <%= ordine.getNumeroCivico() %></p>
        <p>CAP: <%= ordine.getCap() %></p>
        <p>Telefono: <%= ordine.getTelefono() %></p>
        <p>Metodo di Pagamento: <%= ordine.getNomeMetodoPagamento() %></p>
        <p>Metodo di Spedizione: <%= ordine.getNomeMetodoSpedizone() %></p>
        <p>Prezzo Totale: €<%= String.format("%.2f", ordine.getPrezzo() / 100.0).replace('.', ',') %></p>
        <form action="show-items-order" method="get">
            <input type="hidden" name="idOrdine" value="<%= ordine.getIdOrdine() %>">
            <button type="submit">Visualizza i prodotti dell'ordine</button>
        </form>
    </div>
    <% } %>
</div>

<%@include file="footer.jsp"%>

</body>
</html>