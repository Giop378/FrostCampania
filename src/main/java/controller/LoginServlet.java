package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.CarrelloDAO;
import model.DAO.CategoriaDAO;
import model.DAO.UtenteDAO;
import model.beans.Carrello;
import model.beans.Categoria;
import model.beans.Utente;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");//col parametro action posso capire se si vuole fare un login o una registrazione
        Utente utenteSession = (Utente)request.getSession().getAttribute("utente");
        if ( utenteSession!= null ) {//se c'è già un utente nella sessione non c'è bisogno di fare login quindi si va o all'area admin o all'area profilo utente
            if( utenteSession.isAdminCheck() ){
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                requestDispatcher.forward(request, response);
            }else{
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profile.jsp");
                requestDispatcher.forward(request, response);
            }
        } else if ( "register".equals(action) && utenteSession == null ) {//in questo caso si tratta di una registrazione(non ci si può registrare come admin per motivi di sicurezza deve essere fatta la modifica nel database)
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

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profile.jsp");
            requestDispatcher.forward(request, response);
        } else if ( "login".equals(action) && utenteSession == null) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UtenteDAO utenteDAO = new UtenteDAO();
            Utente utente = utenteDAO.doRetrieveByEmailPassword(email, password);
            request.getSession().setAttribute("utente", utente);
            if ( utente == null ) {//nel caso provo a fare login ma l'utente non esiste lo si rimanda alla pagina di login
                //String errorMessage = "Utente non esistente";
                //request.setAttribute("errorMessage", errorMessage);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/login.jsp");
                requestDispatcher.forward(request, response);
            } else if ( utente.isAdminCheck() ) {//se l'utente esiste nel database ed è admin deve andare alla pagina admin
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                requestDispatcher.forward(request, response);
            } else{//se l'utente esiste e non è admin deve andare alla pagina profilo utente
                //questa parte si occupa di prendere i prodotti del carrello da DB e unirli con quelli in sessione
                List<Carrello> carrelloSession = (List<Carrello>) request.getSession().getAttribute("carrello");
                CarrelloDAO carrelloDAO = new CarrelloDAO();
                List<Carrello> carrelloDB = carrelloDAO.doRetrieveByIdUtente(utente.getIdUtente());
                mergeCarrello(carrelloDB, carrelloSession);
                request.getSession().setAttribute("carrello", carrelloDB);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/profile.jsp");
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

    private void mergeCarrello(List<Carrello> carrelloDB, List<Carrello> carrelloSession) {
        Map<Integer, Carrello> map = new HashMap<>();
        //per evitare di avere errori se una delle due liste è null la inizializzo come lista vuota
        if (carrelloDB == null) {
            carrelloDB = new ArrayList<>();
        }
        if (carrelloSession == null) {
            carrelloSession = new ArrayList<>();
        }
        for (Carrello carrello : carrelloDB) {
            map.put(carrello.getIdProdotto(), carrello);
        }

        for (Carrello carrello : carrelloSession) {
            Carrello existingCarrello = map.get(carrello.getIdProdotto());

            if (existingCarrello == null) {
                carrelloDB.add(carrello);
            } else {
                int totalQuantita = existingCarrello.getQuantità() + carrello.getQuantità();
                existingCarrello.setQuantità(totalQuantita);
            }
        }
    }
}
