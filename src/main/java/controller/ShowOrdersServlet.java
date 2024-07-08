package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.CategoriaDAO;
import model.DAO.OrdineDAO;
import model.DAO.ProdottoDAO;
import model.beans.Categoria;
import model.beans.Ordine;
import model.beans.Prodotto;
import model.beans.Utente;

import java.io.IOException;
import java.util.List;
@WebServlet(name = "ShowOrdersServlet", value = "/show-orders")
public class ShowOrdersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if(utente == null){
            throw new MyServletException("Non puoi accedere a questa pagina. Accedi per continuare");
        }
        //nel caso che è un utente normale si mostrano il suo storico ordini
        if( !utente.isAdminCheck() ){
            OrdineDAO ordineDAO = new OrdineDAO();
            List<Ordine> ordini = ordineDAO.doRetrieveByCustomer(utente.getIdUtente());
            request.setAttribute("ordini", ordini);
        }else{
            OrdineDAO ordineDAO = new OrdineDAO();
            List<Ordine> ordini = ordineDAO.doRetrieveAll();
            request.setAttribute("ordini", ordini);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/show-orders.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
