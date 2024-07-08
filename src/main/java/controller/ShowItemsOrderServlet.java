package controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO.ItemOrdineDAO;
import model.DAO.OrdineDAO;
import model.beans.ItemOrdine;
import model.beans.Ordine;
import model.beans.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowItemsOrderServlet", value = "/show-items-order")
public class ShowItemsOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrdine;
        try {
            idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
        }catch (NumberFormatException e){
            throw new MyServletException("Formato inserito non valido");
        }


        Utente utente = (Utente) request.getSession().getAttribute("utente");
        ItemOrdineDAO itemOrdineDAO = new ItemOrdineDAO();

        //Se l'utente è un admin può controllare qualsiasi ordine
        if( utente.isAdminCheck() ){
            List<ItemOrdine> itemOrdineList= itemOrdineDAO.doRetrieveByOrdine(idOrdine);
            request.setAttribute("itemOrdineList", itemOrdineList);
            if(itemOrdineList.isEmpty()){
                throw new MyServletException("Ordine non esistente");
            }
        }
        //Nel caso che è un utente normale si mostra il suo storico ordini (si deve però controllare se l'utente può vedere quella lista di ordini tramite un ciclo for)
        else{
            OrdineDAO ordineDAO = new OrdineDAO();
            List<Ordine> ordini = ordineDAO.doRetrieveByCustomer(utente.getIdUtente());
            Ordine ordine = ordineDAO.doRetrieveByIdOrder(idOrdine);
            if(ordini.contains(ordine)){
                List<ItemOrdine> itemOrdineList = itemOrdineDAO.doRetrieveByOrdine(idOrdine);
                request.setAttribute("itemOrdineList", itemOrdineList);
            }else{
                throw new MyServletException("Non puoi visualizzare questo ordine perchè non fa parte dei tuoi ordini");
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/results/show-items-order.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
