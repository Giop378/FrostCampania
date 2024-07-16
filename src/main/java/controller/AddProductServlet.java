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
import jakarta.servlet.http.*;
import model.DAO.CategoriaDAO;
import model.DAO.ProdottoDAO;
import model.beans.Prodotto;
import model.beans.Utente;

@WebServlet("/add-product-servlet")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    private static final String CARTELLA_UPLOAD = "./images/prodotti";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10 MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if(utente == null || !utente.isAdminCheck()){
            throw new MyServletException("Non puoi accedere a questa pagina");
        }
        //Controlli dei parametri lato server
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Prodotto nuovoProdotto = new Prodotto();
        try {
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            int prezzo = Integer.parseInt(request.getParameter("prezzo"));
            boolean inVetrina = request.getParameter("vetrina") != null; // Se il parametro è true inVetrina è true se vetrina è null inVetrina è false
            int sconto = Integer.parseInt(request.getParameter("sconto"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            String nomeCategoria = request.getParameter("nomecategoria");
            if(categoriaDAO.doRetrieveByNomeCategoria(nomeCategoria)==null) {
                throw new MyServletException("Categoria non esistente");
            }
            if(nome==null|| descrizione==null || nomeCategoria==null ){
                throw new MyServletException("Non sono accettati valori null");
            }
            if(sconto <= 0 || prezzo <= 0 || sconto>=99 || quantita <= 0){
                throw new MyServletException("Valori inseriti non sono accettabili");
            }


            nuovoProdotto.setVetrina(inVetrina);
            nuovoProdotto.setNomeCategoria(nomeCategoria);
            nuovoProdotto.setPrezzo(prezzo);
            nuovoProdotto.setSconto(sconto);
            nuovoProdotto.setQuantità(quantita);
            nuovoProdotto.setDescrizione(descrizione);
            nuovoProdotto.setNome(nome);
        } catch (NumberFormatException e) {
            //nel caso ci sia un errore di formato nei dati inviati dal form
            throw new MyServletException("Uno o più parametri nel form non validi");
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

        // Inoltra la richiesta al risultato della pagina JSP
        String successMessage = "Prodotto aggiunto con successo";
        request.setAttribute("successMessage", successMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/results/confirm-operation.jsp");
        requestDispatcher.forward(request, response);
    }

}