package model.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.beans.*;
public class CarrelloDAO {
    //ritorna i prodotti inseriti nel carrello da quell'utente
    public List<Carrello> doRetrieveByIdUtente(int idUtente) {
        List<Carrello> carrelli = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM carrello WHERE IdUtente = ?"
            );
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Carrello carrello = new Carrello();
                carrello.setIdUtente(rs.getInt("IdUtente"));
                carrello.setIdProdotto(rs.getInt("IdProdotto"));
                carrello.setQuantità(rs.getInt("quantità"));
                carrelli.add(carrello);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carrelli;
    }
    //salva gli elementi del carrello nel database
    public void doSave(List<Carrello> carrelli) {
        try (Connection con = ConPool.getConnection()) {
            for (Carrello carrello : carrelli) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO carrello (IdUtente, IdProdotto, quantità) VALUES(?,?,?)"
                );
                ps.setInt(1, carrello.getIdUtente());
                ps.setInt(2, carrello.getIdProdotto());
                ps.setInt(3, carrello.getQuantità());
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //cancella i prodotti nel carrello di un utente
    public void doDelete(int idUtente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM carrello WHERE IdUtente = ?"
            );
            ps.setInt(1, idUtente);
            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
