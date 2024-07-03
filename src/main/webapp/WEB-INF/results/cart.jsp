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
</head>
<body>
<%@ include file="/WEB-INF/results/header.jsp" %>
<div class="container">
    <% List<Carrello> carrello = (List<Carrello>) session.getAttribute("carrello");
        for(Carrello prodottoCarrello : carrello){ %>
    <div class="product">
        <div class="product-image">
            <img src="<%= prodottoCarrello.getImmagineProdotto() %>" alt="<%= prodottoCarrello.getNomeProdotto() %>" class="product-img">
        </div>
        <div class="product-details">
            <div><%= prodottoCarrello.getNomeProdotto() %></div>
            <div class="product-quantity">
                Quantità: <input type="number" value="<%= prodottoCarrello.getQuantità() %>">
                <button>Rimuovi</button>
            </div>
        </div>
        <div class="product-price">
            <div>Prezzo: <%= String.format("%.2f", prodottoCarrello.getPrezzoProdotto() / 100.0).replace('.', ',') %></div>
        </div>
    </div>
    <% } %>

    <div class="shipping-total-container">
        <div class="shipping">
            <label for="shipping">Spedizione: </label>
            <select id="shipping">
                <option value="standard">Standard</option>
                <option value="express">Express</option>
            </select>
        </div>
        <div class="total">
            Prezzo Totale: €45.00
        </div>
    </div>

    <div class="checkout">
        <button>Procedi con il checkout</button>
    </div>
</div>
<%@ include file="/WEB-INF/results/footer.jsp" %>
</body>
</html>
    





