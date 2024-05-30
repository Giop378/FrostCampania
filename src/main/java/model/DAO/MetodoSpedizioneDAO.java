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
}
