package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.ProdottoDAO;
import model.beans.Carrello;
import model.beans.Prodotto;
import model.beans.Utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "RemoveProductCartServlet", value = "/remove-product-cart")
public class RemoveProductCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Carrello> carrelloSession = (List<Carrello>) session.getAttribute("carrello");

        if (carrelloSession != null) {
            int idProdotto;
            try {
                idProdotto= Integer.parseInt(request.getParameter("idProdotto"));
            }catch(NumberFormatException ex){
                throw new MyServletException("Errore in idProdotto: non è un numero!!");
            }

            for (int i = 0; i < carrelloSession.size(); i++) {
                if (carrelloSession.get(i).getIdProdotto() == idProdotto) {
                    carrelloSession.remove(i);
                    break;// Assumendo che ogni prodotto sia unico, interrompi il ciclo dopo aver rimosso l'elemento.
                }
            }
            session.setAttribute("carrello", carrelloSession);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/cart.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
