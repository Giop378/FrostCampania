package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.CategoriaDAO;
import model.DAO.ProdottoDAO;
import model.beans.Categoria;
import model.beans.Prodotto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/prodotto")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int  idProdotto = Integer.parseInt(request.getParameter("id"));
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);

        if (prodotto != null) {
            request.setAttribute("prodotto", prodotto);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/product.jsp");
            dispatcher.forward(request, response);
        } else {
            // Se il prodotto non è trovato, mostra un messaggio di errore generale
            request.setAttribute("errorMessage", "Il prodotto specificato non è stato trovato.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/general-error.jsp");
            dispatcher.forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}



