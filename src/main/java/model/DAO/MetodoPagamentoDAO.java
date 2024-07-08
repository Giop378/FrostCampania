package model.DAO;

import model.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MetodoPagamentoDAO {
    // Recupera una lista di tutti i metodi di pagamento dal database
    public List<MetodoPagamento> doRetrieveAll() {


        try (Connection con = ConPool.getConnection()) {



            PreparedStatement ps = con.prepareStatement("select nome from metodopagamento");
            ResultSet rs = ps.executeQuery();

            List<MetodoPagamento>  metodiPagamenti= new ArrayList<>();

            while (rs.next()) {

                MetodoPagamento c = new MetodoPagamento();

                c.setNome(rs.getString(1));


                metodiPagamenti.add(c);
            }

            return metodiPagamenti;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    //restituisce l'oggetto metodopagamento dato il nome
    public MetodoPagamento doRetrieveByName(String nomeMetodo) {
        MetodoPagamento metodoPagamento = null;

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nome FROM metodopagamento WHERE nome = ?");
            ps.setString(1, nomeMetodo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                metodoPagamento = new MetodoPagamento();
                metodoPagamento.setNome(rs.getString("nome"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero del metodo di pagamento per nome: " + nomeMetodo, e);
        }

        return metodoPagamento;
    }
}

