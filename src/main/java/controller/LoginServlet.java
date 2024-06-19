package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.UtenteDAO;
import model.beans.Utente;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");//col parametro action posso capire se si vuole fare un login o una registrazione
        if ( request.getSession().getAttribute("utente") != null ) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profilo.jsp");
            requestDispatcher.forward(request, response);
        } else if ( "register".equals(action) ) {//in questo caso si tratta di una registrazione
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            LocalDate dataDiNascita = LocalDate.parse(request.getParameter("datadinascita"));
            Boolean admin = false;

            Utente utente = new Utente();
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setEmail(email);
            utente.setPasswordhash(password);
            utente.setDataDiNascita(dataDiNascita);
            utente.setAdminCheck(admin);

            request.getSession().setAttribute("utente", utente);

            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.doSave(utente);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profilo.jsp");
            requestDispatcher.forward(request, response);
        } else if ( "login".equals(action) ) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UtenteDAO utenteDAO = new UtenteDAO();
            Utente utente = utenteDAO.doRetrieveByEmailPassword(email, password);
            if ( utente == null ) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/general-error.jsp");
                requestDispatcher.forward(request, response);
            } else if ( utente.isAdminCheck() ) {//nel caso che l'utente è admin deve andare alla pagina admin
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                requestDispatcher.forward(request, response);
            } else{//altrimenti va alla pagina utente normale
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profilo.jsp");
            requestDispatcher.forward(request, response);
            }
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/general-error.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
