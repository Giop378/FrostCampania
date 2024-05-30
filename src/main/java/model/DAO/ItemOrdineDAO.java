package model.DAO;
import model.beans.ConPool;
import model.beans.ItemOrdine;
import model.beans.MetodoPagamento;
import model.beans.Ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ItemOrdineDAO {


    // Salva un nuovo item ordine nel database
    public void doSave(ItemOrdine itemOrdine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO itemordine (prezzo, quantità, sconto, IdProdotto, IdOrdine) " +
                            "VALUES (?, ?, ?, ?, ?)");

            ps.setInt(1, itemOrdine.getPrezzo());
            ps.setInt(2, itemOrdine.getQuantità());
            ps.setInt(3, itemOrdine.getSconto());
            ps.setInt(4, itemOrdine.getIdProdotto());
            ps.setInt(5, itemOrdine.getIdOrdine());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera una lista di item ordine per un dato id ordine dal database
    public List<ItemOrdine> doRetrieveByOrdine(int idOrdine) {
        try (Connection con = ConPool.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT IdItemOrdine, prezzo, quantità, sconto, IdProdotto, IdOrdine " +
                            "FROM itemordine " +
                            "WHERE IdOrdine = ?");

            ps.setInt(1, idOrdine);
            ResultSet rs = ps.executeQuery();
            List<ItemOrdine> itemOrdini = new ArrayList<>();

            while (rs.next()) {
                ItemOrdine itemOrdine = new ItemOrdine();
                itemOrdine.setIdItemOrdine(rs.getInt(1));
                itemOrdine.setPrezzo(rs.getInt(2));
                itemOrdine.setQuantità(rs.getInt(3));
                itemOrdine.setSconto(rs.getInt(4));
                itemOrdine.setIdProdotto(rs.getInt(5));
                itemOrdine.setIdOrdine(rs.getInt(6));

                itemOrdini.add(itemOrdine);
            }

            return itemOrdini;

        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }
}
