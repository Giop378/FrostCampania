package controller;

import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ACMPEQ;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.CategoriaDAO;
import model.DAO.ProdottoDAO;
import model.beans.Prodotto;
import model.beans.Utente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

@WebServlet(name="ModifyProductServlet", value="/modify-product-servlet")
public class ModifyProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente == null || !utente.isAdminCheck()){
            throw new MyServletException("Non è possibile accedere a questa pagina");
        }
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto prodottoModificato = new Prodotto();
        try {
            int idProdotto = Integer.parseInt(request.getParameter("id"));
            if(prodottoDAO.doRetrieveById(idProdotto) == null){
                throw new MyServletException("Prodotto non esistente");
            }
            String immagine=prodottoDAO.doRetrieveById(idProdotto).getImmagine();
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            int prezzo = Integer.parseInt(request.getParameter("prezzo"));
            boolean inVetrina = request.getParameter("vetrina") != null; // checkbox
            int sconto = Integer.parseInt(request.getParameter("sconto"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            String nomeCategoria = request.getParameter("nomecategoria");
            //controlli delle stringe lato server
            if(categoriaDAO.doRetrieveByNomeCategoria(nomeCategoria)==null){
                throw new ServletException("Categoria non esistente");
            }
            if(nome==null|| descrizione==null || nomeCategoria==null ){
                throw new MyServletException("Non sono accettati valori null");
            }
            if(sconto <= 0 || prezzo <= 0 || sconto>=99 || quantita <= 0){
                throw new MyServletException("Valori inseriti non sono accettabili");
            }
            prodottoModificato.setIdProdotto(idProdotto);
            prodottoModificato.setNomeCategoria(nomeCategoria);
            prodottoModificato.setPrezzo(prezzo);
            prodottoModificato.setSconto(sconto);
            prodottoModificato.setQuantità(quantita);
            prodottoModificato.setImmagine(immagine);
            prodottoModificato.setDescrizione(descrizione);
            prodottoModificato.setNome(nome);
            prodottoModificato.setVetrina(inVetrina);
            prodottoDAO.doUpdate(prodottoModificato);
        } catch (NumberFormatException e) {
            throw new MyServletException("Uno o più parametri errati nel form");
        }
        String successMessage = "Prodotto modificato con successo";
        request.setAttribute("successMessage", successMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/results/confirm-operation.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
