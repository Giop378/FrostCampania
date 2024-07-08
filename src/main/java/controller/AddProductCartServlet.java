package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.ProdottoDAO;
import model.beans.Carrello;
import model.beans.Prodotto;
import model.beans.Utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddProductCartServlet", value = "/add-product-cart")
public class AddProductCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Carrello> carrelloSession = (List<Carrello>) session.getAttribute("carrello");
        if (carrelloSession == null) {
            carrelloSession = new ArrayList<>();
            session.setAttribute("carrello", carrelloSession);
        }

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        // Si prende l'id passato dal client e si recupera tramite DAO il relativo prodotto
        Prodotto p = prodottoDAO.doRetrieveById(Integer.parseInt(request.getParameter("idProdotto")));
        if(p==null){
            throw new MyServletException("Il prodotto non esiste");
        }
        int quantità = Integer.parseInt(request.getParameter("quantità"));
        Carrello carrello = new Carrello();
        if (session.getAttribute("utente") == null) {
            carrello.setIdUtente(null);
        } else {
            Utente utente = (Utente) session.getAttribute("utente");
            if(utente.isAdminCheck()){
                throw new MyServletException("L'admin non può interagire con il carrello");
            }
            int idUtente = utente.getIdUtente();
            carrello.setIdUtente(idUtente);
        }
        carrello.setPrezzoProdotto(p.getPrezzo());
        carrello.setImmagineProdotto(p.getImmagine());
        carrello.setNomeProdotto(p.getNome());
        carrello.setIdProdotto(p.getIdProdotto());
        //Prima di inserire la quantità controllo che effettivamente ci sono in magazzino abbastanza prodotti
        if(quantità > p.getQuantità()){
            throw new MyServletException("Quantità selezionata del prodotto non presente in magazzino");
        }
        carrello.setQuantità(quantità);

        // Controlla se il prodotto è già presente nel carrello
        boolean prodottoTrovato = false;
        for (Carrello item : carrelloSession) {
            if (item.getIdProdotto() == carrello.getIdProdotto()) {
                // Aggiorna la quantità del prodotto esistente
                item.setQuantità(item.getQuantità() + quantità);
                prodottoTrovato = true;
                break;
            }
        }

        // Se il prodotto non è trovato, aggiungilo al carrello
        if (!prodottoTrovato) {
            carrelloSession.add(carrello);
        }

        session.setAttribute("carrello", carrelloSession);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/cart.jsp");
        requestDispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
