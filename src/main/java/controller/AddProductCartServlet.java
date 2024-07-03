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
        if (carrelloSession==null) {
            carrelloSession = new ArrayList<Carrello>();
            session.setAttribute("carrello", carrelloSession);
        }
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        //si prendere l'id passato dal client e prende tramite dao il relativo prodotto
        Prodotto p = prodottoDAO.doRetrieveById(Integer.parseInt(request.getParameter("idProdotto")));
        int quantità = Integer.parseInt(request.getParameter("quantità"));
        //ora si prende il prodotto e la quantità e si crea un oggetto carrello da aggiungere in sessione alla restante lista
        Carrello carrello = new Carrello();
        if(session.getAttribute("utente")==null){
            carrello.setIdUtente(null);
        }else{
            Utente utente = (Utente) session.getAttribute("utente");
            int idUtente = utente.getIdUtente();
            carrello.setIdUtente(idUtente);
        }
        carrello.setPrezzoProdotto(p.getPrezzo());
        carrello.setImmagineProdotto(p.getImmagine());
        carrello.setNomeProdotto(p.getNome());
        carrello.setIdProdotto(p.getIdProdotto());
        carrello.setQuantità(quantità);
        carrelloSession.add(carrello);
        session.setAttribute("carrello", carrelloSession);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/cart.jsp");
        requestDispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
