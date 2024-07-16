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

        if ( utente == null || carrello == null || carrello.isEmpty() || utente.isAdminCheck() ) {
            throw new MyServletException("Conferma ordine non possibile: non puoi accedere a questa pagina");
        }

        // Recupera i dettagli dell'ordine dal form e controlla se sono corretti
        String nome;
        String cognome;
        String via;
        int numeroCivico;
        int cap;
        String telefono;
        MetodoPagamento metodoPagamento;
        MetodoSpedizione metodoSpedizione;
        try {
            nome = request.getParameter("nome");
            cognome = request.getParameter("cognome");
            via = request.getParameter("via");
            String numeroCivicoStr = request.getParameter("numerocivico");
            String capStr = request.getParameter("cap");
            telefono = request.getParameter("telefono");
            MetodoPagamentoDAO metodoPagamentoDAO = new MetodoPagamentoDAO();
            metodoPagamento = metodoPagamentoDAO.doRetrieveByName(request.getParameter("metodoPagamento"));
            MetodoSpedizioneDAO metodoSpedizioneDAO = new MetodoSpedizioneDAO();
            metodoSpedizione = metodoSpedizioneDAO.doRetrieveByName(request.getParameter("metodoSpedizione"));
            if (nome == null || cognome == null || via == null || telefono == null || metodoPagamento == null || metodoSpedizione == null) {
                throw new MyServletException("Tutti i campi sono obbligatori");
            }
            //Controllo dei giusti formati
            if (!nome.matches("[a-zA-Z ]+") || !cognome.matches("[a-zA-Z ]+")) {
                throw new MyServletException("Nome o cognome non validi.");
            }
            if (!numeroCivicoStr.matches("[0-9]{1,4}") || !capStr.matches("[0-9]{5}")) {
                throw new MyServletException("Numero civico o CAP non validi.");
            }
            numeroCivico=Integer.parseInt(numeroCivicoStr);
            cap = Integer.parseInt(capStr);
            if (telefono != null && !telefono.matches("[0-9]{9,15}")) {
                throw new MyServletException("Numero di telefono non valido.");
            }
        } catch (NumberFormatException ex) {
            throw new MyServletException("Parametri in input non validi");
        }
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
            if ( itemCarrello.getQuantità() > prodotto.getQuantità() ) {
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

        //Rimuove il carrello dal database
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        carrelloDAO.doDelete(utente.getIdUtente());


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