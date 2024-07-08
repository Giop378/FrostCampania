package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.beans.Carrello;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Carrello> carrelloSession = (List<Carrello>) session.getAttribute("carrello");

        // Leggere il corpo della richiesta JSON
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder jsonInput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonInput.append(line);
        }

        // Parsare il corpo JSON utilizzando json-simple
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonInput.toString());

            int idProdotto = Integer.parseInt(String.valueOf(jsonObject.get("idProdotto")));
            int nuovaQuantita = Integer.parseInt(String.valueOf(jsonObject.get("quantita")));
            if(nuovaQuantita<=0){
                nuovaQuantita=1;
            }
            // Trova e aggiorna la quantità del prodotto nel carrello
            for (Carrello prodottoCarrello : carrelloSession) {
                if (prodottoCarrello.getIdProdotto() == idProdotto) {
                    prodottoCarrello.setQuantità(nuovaQuantita);
                    break;
                }
            }

            // Aggiorna la sessione con il carrello modificato
            session.setAttribute("carrello", carrelloSession);

            // Invia una risposta JSON di successo (se necessario)
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();

        } catch (ParseException e) {
            e.printStackTrace();
            // Gestione dell'errore di parsing JSON
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
        }
    }
}