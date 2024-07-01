package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.DAO.ProdottoDAO;
import model.beans.Prodotto;

@WebServlet("/add-product-servlet")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    private static final String CARTELLA_UPLOAD = "./images/prodotti";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Prodotto nuovoProdotto = new Prodotto();
        try {
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            int prezzo = Integer.parseInt(request.getParameter("prezzo"));
            boolean inVetrina = request.getParameter("vetrina") != null; // checkbox
            int sconto = Integer.parseInt(request.getParameter("sconto"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            String nomeCategoria = request.getParameter("nomecategoria");
            nuovoProdotto.setNomeCategoria(nomeCategoria);
            nuovoProdotto.setPrezzo(prezzo);
            nuovoProdotto.setSconto(sconto);
            nuovoProdotto.setQuantità(quantita);
            nuovoProdotto.setDescrizione(descrizione);
            nuovoProdotto.setNome(nome);
        } catch (NumberFormatException e) {
            //nel caso ci sia un errore di formato nei dati inviati dal form
            request.setAttribute("errorMessage", "Uno o più formati del form non sono corretti");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/general-error.jsp");
            dispatcher.forward(request, response);
        }

        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Verifica dell'estensione del file
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            request.setAttribute("errorMessage", "Tipo di file non supportato. Carica un file con estensione JPG, JPEG, PNG o GIF.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/general-error.jsp");
            dispatcher.forward(request, response);
        }

        // Verifica della dimensione del file
        if (filePart.getSize() > MAX_FILE_SIZE) {
            request.setAttribute("errorMessage", "Dimensione del file superiore al limite consentito (10MB).");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/general-error.jsp");
            dispatcher.forward(request, response);
        }

        // Path di destinazione del file
        String destinazione = CARTELLA_UPLOAD + File.separator + fileName;
        Path pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));

        // Rinomina il file se esiste già un file con lo stesso nome
        for (int i = 2; Files.exists(pathDestinazione); i++) {
            destinazione = CARTELLA_UPLOAD + File.separator + i + "_" + fileName;
            pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));
        }

        // Scrive il file nella destinazione
        try (InputStream fileInputStream = filePart.getInputStream()) {
            Files.createDirectories(pathDestinazione.getParent());
            Files.copy(fileInputStream, pathDestinazione);
        }catch(IOException e){//caso in cui c'è un errore nella scrittura dell'immagine nella cartella
            request.setAttribute("errorMessage", "Errore durante il caricamento del file. Riprova più tardi.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/general-error.jsp");
            dispatcher.forward(request, response);
        }

        nuovoProdotto.setImmagine(destinazione);
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        prodottoDAO.doSave(nuovoProdotto);
        request.setAttribute("uploaded", destinazione);

        // Inoltra la richiesta al risultato della pagina JSP
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/results/uploadResult.jsp");
        requestDispatcher.forward(request, response);
    }

}