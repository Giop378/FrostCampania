package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.CategoriaDAO;
import model.beans.Categoria;
import model.beans.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet("/user-servlet")
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Utente utente =(Utente) session.getAttribute("utente");
        if(utente == null ){//caso in cui l'utente non ha ancora fatto l'accesso ma deve fare la login
            if("register".equals(action)){
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/register.jsp");
                requestDispatcher.forward(request, response);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/login.jsp");
            requestDispatcher.forward(request, response);


        }else if (utente.isAdminCheck()){//caso in cui l'utente è già loggato ed è un admin
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/admin.jsp");
            requestDispatcher.forward(request, response);
        }else if (!utente.isAdminCheck()){//caso in cui l'utente non è un admin
            session.setAttribute("utente", utente);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profile.jsp");
            requestDispatcher.forward(request, response);
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }

}
