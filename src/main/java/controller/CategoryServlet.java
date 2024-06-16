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
    private Categoria findCategoriaByName(List<Categoria> categorie, String nomeCategoria) {
        for (Categoria categoria : categorie) {
            if (categoria.getNome().equalsIgnoreCase(nomeCategoria)) {
                return categoria;
            }
        }
        return null; // Se non trova la categoria, restituisce null
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeCategoria = request.getParameter("categoria");
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        List<Prodotto> prodottiPerCategoria = prodottoDAO.doRetrieveByCategory(nomeCategoria);
        List<Categoria> categorie = categoriaDAO.doRetrieveAll();

        //cerca la categoria per nome
        Categoria categoriaSel = findCategoriaByName(categorie, nomeCategoria);

        if (categoriaSel != null) {

            request.setAttribute("nomeCategoria", nomeCategoria);
            request.setAttribute("prodottiPerCategoria", prodottiPerCategoria);
            request.setAttribute("categorie", categorie);
            request.setAttribute("categoria", categoriaSel);




            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/category.jsp");
            dispatcher.forward(request, response);
        } else {
            // Se non trova la categoria, reindirizza a una pagina di errore
            response.sendRedirect(request.getContextPath() + "/WEB-INF/results/general-error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}


