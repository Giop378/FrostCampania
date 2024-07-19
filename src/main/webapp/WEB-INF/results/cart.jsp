<%@ page import="model.beans.Carrello" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="./css/cartStyles.css">
    <script src="./script/updateQuantityCart.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/results/header.jsp" %>
<div class="content-wrapper">
    <div class="container">
        <% List<Carrello> carrello = (List<Carrello>) session.getAttribute("carrello");
            int prezzoTotale = 0;
            for(Carrello prodottoCarrello : carrello){
                prezzoTotale += prodottoCarrello.getPrezzoProdotto() * prodottoCarrello.getQuantità();
        %>
        <div class="product">
            <div class="product-image">
                <img src="<%= prodottoCarrello.getImmagineProdotto() %>" alt="<%= prodottoCarrello.getNomeProdotto() %>" class="product-img">
            </div>
            <div class="product-details" id="">
                <div id="product-name"><%= prodottoCarrello.getNomeProdotto() %></div>
            </div>
            <div class="product-action">
                <div class="product-quantity">
                    Quantità: <input type="number" id="<%= prodottoCarrello.getIdProdotto() %>" name="quantita" value="<%= prodottoCarrello.getQuantità() %>" min="1" step="1" onchange="updateQuantita(<%= prodottoCarrello.getIdProdotto() %>)" aria-labelledby="product-name">

                    <form action="remove-product-cart" method="post">
                        <input type="hidden" name="idProdotto" value="<%= prodottoCarrello.getIdProdotto() %>">
                        <button type="submit" class="remove-button">Rimuovi</button>
                    </form>
                </div>
                <div class="product-price">
                    Prezzo(Singola unità): €<%= String.format("%.2f", prodottoCarrello.getPrezzoProdotto() / 100.0).replace('.', ',') %>
                </div>
            </div>
        </div>
        <% } %>

        <div class="total-container">
            <div class="total">
                Prezzo Totale: €<%= String.format("%.2f", prezzoTotale / 100.0).replace('.', ',') %>
            </div>
        </div>

        <div class="checkout">
            <form action="checkout-servlet" method="post">
                <button type="submit">Procedi con il checkout</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/results/footer.jsp" %>
</body>
</html>


    





