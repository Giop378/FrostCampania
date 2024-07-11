package controller;

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

import java.io.IOException;
@WebServlet(name="DeleteProductServlet", value="/delete-product-servlet")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente == null || !utente.isAdminCheck()){
            throw new MyServletException("Non è possibile accedere a questa pagina");
        }
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        try {
            int idProdotto = Integer.parseInt(request.getParameter("id"));
            if(prodottoDAO.doRetrieveById(idProdotto) == null){
                throw new MyServletException("Prodotto non esistente");
            }
            prodottoDAO.doDelete(idProdotto);
        } catch (NumberFormatException e) {
            throw new MyServletException("Errore di formato nei dati inseriti nel form");
        }
        String successMessage = "Prodotto eliminato con successo";
        request.setAttribute("successMessage", successMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/results/confirm-operation.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
