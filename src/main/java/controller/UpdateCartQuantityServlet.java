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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

@WebServlet("/update-cart-quantity")
public class UpdateCartQuantityServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");

        synchronized (session) {
            List<Carrello> carrelloSession = (List<Carrello>) session.getAttribute("carrello");

            JSONParser parser = new JSONParser();
            try(PrintWriter out = response.getWriter();) {
                int idProdotto= Integer.parseInt(request.getParameter("idProdotto"));
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                Prodotto prodotto = prodottoDAO.doRetrieveById(idProdotto);
                if(prodotto == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    JSONObject error = new JSONObject();
                    error.put("message", "Prodotto non esistente");
                    out.print(error.toJSONString());
                    out.flush();
                    return;
                } else {
                    int nuovaQuantita = Integer.parseInt(request.getParameter("nuovaQuantita"));
                    if(nuovaQuantita <= 0) {
                        nuovaQuantita = 1;
                    }

                    boolean prodottoTrovato = false;
                    for (Carrello prodottoCarrello : carrelloSession) {
                        if (prodottoCarrello.getIdProdotto() == idProdotto) {
                            if (nuovaQuantita > prodotto.getQuantità()) {
                                nuovaQuantita=prodotto.getQuantità();
                            }

                            prodottoCarrello.setQuantità(nuovaQuantita);
                            JSONObject result = new JSONObject();
                            result.put("nuovaQuantita", nuovaQuantita);
                            out.print(result.toJSONString());
                            out.flush();

                            prodottoTrovato = true;
                            break;
                        }
                    }

                    if (!prodottoTrovato) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        JSONObject error = new JSONObject();
                        error.put("message", "Prodotto non trovato nel carrello");
                        out.print(error.toJSONString());
                        out.flush();
                        return;
                    }

                    session.setAttribute("carrello", carrelloSession);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JSONObject error = new JSONObject();
                error.put("message", "Errore durante l'elaborazione della richiesta");
                try (PrintWriter out = response.getWriter()) {
                    out.print(error.toJSONString());
                }
            }
        }
    }
}