package model.DAO;

import model.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MetodoSpedizioneDAO {

    // Recupera una lista di tutti i metodi di spedizione dal database
    public List<MetodoSpedizione> doRetrieveAll() {


        try (Connection con = ConPool.getConnection()) {



            PreparedStatement ps = con.prepareStatement("select nome,descrizione,costo from metodospedizione");
            ResultSet rs = ps.executeQuery();

            List<MetodoSpedizione>  metodiSpedizioni = new ArrayList<>();

            while (rs.next()) {

                MetodoSpedizione c = new MetodoSpedizione();

                c.setNome(rs.getString(1));
                c.setDescrizione(rs.getString(2));
                c.setCosto(rs.getInt(3));

                metodiSpedizioni.add(c);
            }

            return metodiSpedizioni;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    // Recupera un metodo di spedizione dato il nome
    public MetodoSpedizione doRetrieveByName(String nomeMetodo) {
        MetodoSpedizione metodoSpedizione = null;

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nome, descrizione, costo FROM metodospedizione WHERE nome = ?");
            ps.setString(1, nomeMetodo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                metodoSpedizione = new MetodoSpedizione();
                metodoSpedizione.setNome(rs.getString("nome"));
                metodoSpedizione.setDescrizione(rs.getString("descrizione"));
                metodoSpedizione.setCosto(rs.getInt("costo"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero del metodo di spedizione per nome: " + nomeMetodo, e);
        }

        return metodoSpedizione;
    }
}
