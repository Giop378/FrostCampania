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
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/categoria")
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeCategoria = request.getParameter("categoria");
        //Controllo sul parametro
        if(nomeCategoria==null || nomeCategoria.equals("")){
            throw new MyServletException("Parametro categoria non valido");
        }

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Prodotto> prodottiPerCategoria = prodottoDAO.doRetrieveByCategory(nomeCategoria);
        List<Categoria> categorie = categoriaDAO.doRetrieveAll();

        //cerca la categoria per nome
        Categoria categoriaScelta = categoriaDAO.doRetrieveByNomeCategoria(nomeCategoria);

        if (categoriaScelta != null) {

            request.setAttribute("prodottiPerCategoria", prodottiPerCategoria);
            request.setAttribute("categoriaScelta", categoriaScelta);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/category.jsp");
            dispatcher.forward(request, response);
        } else {
            // Se non trova la categoria, reindirizza a una pagina di errore con un messaggio di errore
            throw new MyServletException("La categoria non è stata trovata");
        }
        Prodotto p = new Prodotto();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}


