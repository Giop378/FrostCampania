<%--
  Created by IntelliJ IDEA.
  User: DomeA
  Date: 24/05/2024
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<%@ page import="model.beans.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.beans.Categoria" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Title</title>
    <title>Errore generale</title>
    <link rel="stylesheet" href="./css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="error-container">
    <h1>Qualcosa è andato storto!!</h1>
    <p>Ci scusiamo per l'inconveniente. Si è verificato un errore imprevisto.</p>
    <h4><%= exception.getMessage()%></h4>
    <p>Per favore, prova a:</p>

    <p>Tornare alla <a href="index.html">pagina iniziale</a></p>
    <p> oppure </p>
    <p>Contattare il nostro supporto tecnico se il problema persiste</p>

    <p>Grazie per la tua pazienza e comprensione.</p>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>



