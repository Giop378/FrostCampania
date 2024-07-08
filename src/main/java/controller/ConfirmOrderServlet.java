package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.*;
import model.beans.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/confirm-order-servlet")
public class ConfirmOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        List<Carrello> carrello = (List<Carrello>) session.getAttribute("carrello");

        if (utente == null || carrello == null || carrello.isEmpty()) {
            response.sendRedirect("error-page.jsp"); // Reindirizza ad una pagina di errore se l'utente o il carrello non sono validi
            return;
        }

        // Recupera i dettagli dell'ordine dal form
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String via = request.getParameter("via");
        int numeroCivico = Integer.parseInt(request.getParameter("numerocivico"));
        int cap = Integer.parseInt(request.getParameter("cap"));
        String telefono = request.getParameter("telefono");
        MetodoPagamentoDAO metodoPagamentoDAO = new MetodoPagamentoDAO();
        MetodoPagamento metodoPagamento = metodoPagamentoDAO.doRetrieveByName(request.getParameter("metodoPagamento"));
        MetodoSpedizioneDAO metodoSpedizioneDAO = new MetodoSpedizioneDAO();
        MetodoSpedizione metodoSpedizione = metodoSpedizioneDAO.doRetrieveByName(request.getParameter("metodoSpedizione"));

        // Crea un oggetto Ordine
        Ordine ordine = new Ordine();
        ordine.setIdUtente(utente.getIdUtente());
        ordine.setNome(nome);
        ordine.setCognome(cognome);
        ordine.setVia(via);
        ordine.setNumeroCivico(numeroCivico);
        ordine.setCap(cap);
        ordine.setTelefono(telefono);
        ordine.setNomeMetodoPagamento(metodoPagamento.getNome());
        ordine.setNomeMetodoSpedizone(metodoSpedizione.getNome());
        // Per calcolare il prezzo totale bisogna scorrere tutto il carrello e aggiungere anche il costo della spedizione
        int prezzoTotale = metodoSpedizione.getCosto();
        for (Carrello itemCarrello : carrello) {
            int idProdotto = itemCarrello.getIdProdotto();
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);

            // Controllo se c'è qualche prodotto da ordinare la cui quantità è maggiore di quella nel magazzino
            if (itemCarrello.getQuantità() > prodotto.getQuantità()) {
                throw new MyServletException("Prodotto non più disponibile nelle quantità inserite dall'utente");
            }

            // Diminuisco la quantità di prodotto presente in magazzino e aggiorno il prodotto
            prodotto.setQuantità(prodotto.getQuantità() - itemCarrello.getQuantità());
            prodottoDAO.doUpdate(prodotto);

            prezzoTotale = prezzoTotale + prodotto.getPrezzo() * itemCarrello.getQuantità();
        }
        ordine.setPrezzo(prezzoTotale);

        // Salva l'ordine nel database e recupera l'id dell'ordine appena salvato
        OrdineDAO ordineDAO = new OrdineDAO();
        int ordineId = ordineDAO.doSave(ordine);

        // Converti il carrello in una lista di itemOrdine e salva ogni itemOrdine nel database
        ItemOrdineDAO itemOrdineDAO = new ItemOrdineDAO();
        List<ItemOrdine> itemsOrdine = new ArrayList<>();
        for (Carrello itemCarrello : carrello) {
            int idProdotto = itemCarrello.getIdProdotto();
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);
            ItemOrdine itemOrdine = new ItemOrdine();
            itemOrdine.setIdOrdine(ordineId);
            itemOrdine.setNome(prodotto.getNome());
            itemOrdine.setImmagine(prodotto.getImmagine());
            itemOrdine.setPrezzo(prodotto.getPrezzo());
            itemOrdine.setSconto(prodotto.getSconto());
            itemOrdine.setQuantità(itemCarrello.getQuantità());
            itemOrdine.setIdProdotto(itemCarrello.getIdProdotto());
            itemOrdineDAO.doSave(itemOrdine);
            itemsOrdine.add(itemOrdine);
        }

        // Svuota il carrello dopo l'invio dell'ordine
        session.removeAttribute("carrello");

        // Set attributes for the JSP
        request.setAttribute("ordine", ordine);
        request.setAttribute("itemsOrdine", itemsOrdine);

        // Reindirizza ad una pagina di conferma dell'ordine
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/confirm-order.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}