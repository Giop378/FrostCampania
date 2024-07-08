package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.CarrelloDAO;
import model.beans.Carrello;
import model.beans.Utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Carrello> carrelloSession = (List<Carrello>) session.getAttribute("carrello");
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente==null){
            throw new MyServletException("Utente non registrato: non puoi fare il logout");
        }

        //Elimino tutti gli elementi nel carrello associati a quell'utente per poi salvare i nuovi elementi carrello che sono salvati in sessione
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        List<Carrello> carrelloDB = carrelloDAO.doRetrieveByIdUtente(utente.getIdUtente());
        if(!carrelloDB.isEmpty()) {
            carrelloDAO.doDelete(utente.getIdUtente());
        }
        carrelloDAO.doSave(carrelloSession);

        session.invalidate();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.html");
        requestDispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
