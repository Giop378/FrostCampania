package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.CategoriaDAO;
import model.DAO.UtenteDAO;
import model.beans.Categoria;
import model.beans.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet("/utente-servlet")
public class UtenteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> categorie = categoriaDAO.doRetrieveAll();
        request.setAttribute("categorie", categorie);
        String action = request.getParameter("action");
        if(request.getSession().getAttribute("utente") == null && !"register".equals(action) ){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/login.jsp");
            requestDispatcher.forward(request, response);
        }else if (request.getSession().getAttribute("utente") == null && "register".equals(action)==true){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/register.jsp");
            requestDispatcher.forward(request, response);
        }else{
            //si dovranno aggiungere i valori da passare a profilo.jsp
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profilo.jsp");
            requestDispatcher.forward(request, response);
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }

}
