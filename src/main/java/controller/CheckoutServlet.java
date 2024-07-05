package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.MetodoPagamentoDAO;
import model.DAO.MetodoSpedizioneDAO;
import model.DAO.ProdottoDAO;
import model.beans.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name="CheckoutServlet", value = "/checkout-servlet")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Carrello> carrelloSession = (List<Carrello>) session.getAttribute("carrello");
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente==null){//L'utente prima di procedere al pagamento deve registrarsi o accedere
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/login.jsp");
            requestDispatcher.forward(request, response);
        }
        //Se l'utente è un admin questo non può fare un ordine
        if(utente.isAdminCheck()){
            throw new MyServletException("L'admin non può fare un ordine");
        }
        //Se il carrello nella sessione è vuoto o è null allora si ritorna alla jsp del carrello
        if(carrelloSession.isEmpty() || carrelloSession==null ){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/cart.jsp");
            requestDispatcher.forward(request, response);
        }
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        //Calcolo il prezzo totale (spedizione esclusa)
        int prezzoTotale = 0;
        for(Carrello c : carrelloSession){
            int idProdotto = c.getIdProdotto();
            Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);
            prezzoTotale = prezzoTotale + prodotto.getPrezzo();
        }
        request.setAttribute("prezzoTotale", prezzoTotale);

        //Mi prendo dal database i vari metodi di pagamento e metodi di spedizione in modo da passarli alla jsp
        MetodoSpedizioneDAO metodoSpedizioneDAO = new MetodoSpedizioneDAO();
        MetodoPagamentoDAO metodoPagamentoDAO = new MetodoPagamentoDAO();
        List<MetodoSpedizione> metodiSpedizione = metodoSpedizioneDAO.doRetrieveAll();
        request.setAttribute("metodiSpedizione", metodiSpedizione);

        List<MetodoPagamento> metodiPagamento = metodoPagamentoDAO.doRetrieveAll();
        request.setAttribute("metodiPagamento", metodiPagamento);


        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/checkout.jsp");
        requestDispatcher.forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
