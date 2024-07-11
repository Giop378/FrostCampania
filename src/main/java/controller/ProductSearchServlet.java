package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO.ProdottoDAO;
import model.beans.Prodotto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class ProductSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO prodottoDAO = new ProdottoDAO(); // Inizializzazione del DAO dei prodotti
        String query = request.getParameter("query");

        // Eseguire la ricerca nel database utilizzando il DAO
        List<Prodotto> risultati = prodottoDAO.doRetrieveByName(query);

        // Convertire la lista di prodotti in formato JSON utilizzando JSON.simple
        JSONArray jsonRisultati = new JSONArray();
        for (Prodotto prodotto : risultati) {
            JSONObject prodottoJson = new JSONObject();
            prodottoJson.put("idProdotto", prodotto.getIdProdotto());
            prodottoJson.put("nome", prodotto.getNome());
            prodottoJson.put("descrizione", prodotto.getDescrizione());
            prodottoJson.put("prezzo", prodotto.getPrezzo());
            prodottoJson.put("vetrina", prodotto.isVetrina());
            prodottoJson.put("immagine", prodotto.getImmagine());
            prodottoJson.put("sconto", prodotto.getSconto());
            prodottoJson.put("quantita", prodotto.getQuantità());
            prodottoJson.put("nomecategoria", prodotto.getNomeCategoria());

            jsonRisultati.add(prodottoJson);
        }

        // Rispondere con i risultati in formato JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRisultati.toJSONString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}