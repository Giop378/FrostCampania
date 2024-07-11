package model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.beans.*;
/*private int idProdotto;
    private String nome;
    private int prezzo;
    private String descrizione;
    private String immagine;
    private boolean vetrina;
    private int sconto;
    private int quantità;
    private String nomeCategoria; */
public class ProdottoDAO {
    //Restituisce tutti i prodotti presenti nel database
    public List<Prodotto> doRetrieveAll() {

        try (Connection con = ConPool.getConnection()) {

            PreparedStatement ps = con.prepareStatement("select idProdotto, nome, prezzo, descrizione, immagine, vetrina, sconto, quantità, nomecategoria from prodotto");
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();

            while (rs.next()) {

                Prodotto p = new Prodotto();
                p.setIdProdotto(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setPrezzo(rs.getInt(3));
                p.setDescrizione(rs.getString(4));
                p.setImmagine(rs.getString(5));
                p.setVetrina(rs.getBoolean(6));
                p.setSconto(rs.getInt(7));
                p.setQuantità(rs.getInt(8));
                p.setNomeCategoria(rs.getString(9));
                prodotti.add(p);
            }

            return prodotti;

        } catch (SQLException s) {

            throw new RuntimeException(s);
        }
    }
    //Restituisce tutti i prodotti di una determinata categoria
    public List<Prodotto> doRetrieveByCategory(String nomeCategoria){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select idProdotto, nome, prezzo, descrizione, immagine, vetrina, sconto, quantità, nomecategoria from prodotto where nomecategoria=?");
            ps.setString(1, nomeCategoria);
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {

                Prodotto p = new Prodotto();
                p.setIdProdotto(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setPrezzo(rs.getInt(3));
                p.setDescrizione(rs.getString(4));
                p.setImmagine(rs.getString(5));
                p.setVetrina(rs.getBoolean(6));
                p.setSconto(rs.getInt(7));
                p.setQuantità(rs.getInt(8));
                p.setNomeCategoria(rs.getString(9));
                prodotti.add(p);
            }

            return prodotti;

        } catch (SQLException s) {

            throw new RuntimeException(s);
        }
    }
    //restituisce gli elementi della vetrina
    public List<Prodotto> doRetrieveVetrina(){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT idProdotto, nome, prezzo, descrizione, immagine, vetrina, sconto, quantità, nomecategoria FROM prodotto WHERE vetrina = true ORDER BY RAND() LIMIT 8");
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();

            while (rs.next()) {

                Prodotto p = new Prodotto();
                p.setIdProdotto(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setPrezzo(rs.getInt(3));
                p.setDescrizione(rs.getString(4));
                p.setImmagine(rs.getString(5));
                p.setVetrina(rs.getBoolean(6));
                p.setSconto(rs.getInt(7));
                p.setQuantità(rs.getInt(8));
                p.setNomeCategoria(rs.getString(9));
                prodotti.add(p);
            }

            return prodotti;

        } catch (SQLException s) {

            throw new RuntimeException(s);
        }
    }
    //Prende i dati di un singolo prodotto dal database
    public Prodotto doRetrieveById(int idProdotto){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select idProdotto, nome, prezzo, descrizione, immagine, vetrina, sconto, quantità, nomecategoria from prodotto where idProdotto=?");
            ps.setInt(1, idProdotto);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Prodotto p = new Prodotto();
                p.setIdProdotto(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setPrezzo(rs.getInt(3));
                p.setDescrizione(rs.getString(4));
                p.setImmagine(rs.getString(5));
                p.setVetrina(rs.getBoolean(6));
                p.setSconto(rs.getInt(7));
                p.setQuantità(rs.getInt(8));
                p.setNomeCategoria(rs.getString(9));
                return p;
            }
            return null;

        } catch (SQLException s) {

            throw new RuntimeException(s);
        }
    }
    //Salva un prodotto nel database
    public void doSave(Prodotto prodotto){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO prodotto (nome, prezzo, descrizione, immagine, vetrina, sconto, quantità, nomecategoria) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, prodotto.getNome());
            ps.setInt(2, prodotto.getPrezzo());
            ps.setString(3, prodotto.getDescrizione());
            ps.setString(4, prodotto.getImmagine());
            ps.setBoolean(5, prodotto.isVetrina());
            ps.setInt(6, prodotto.getSconto());
            ps.setInt(7, prodotto.getQuantità());
            ps.setString(8, prodotto.getNomeCategoria());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Aggiorna un prodotto nel database
    public void doUpdate(Prodotto prodotto){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE Prodotto SET nome=?, prezzo=?, descrizione=?, immagine=?, vetrina=?, sconto=?, quantità=?, nomecategoria=? WHERE IdProdotto=?");
            ps.setString(1, prodotto.getNome());
            ps.setInt(2, prodotto.getPrezzo());
            ps.setString(3, prodotto.getDescrizione());
            ps.setString(4, prodotto.getImmagine());
            ps.setBoolean(5, prodotto.isVetrina());
            ps.setInt(6, prodotto.getSconto());
            ps.setInt(7, prodotto.getQuantità());
            ps.setString(8, prodotto.getNomeCategoria());
            ps.setInt(9, prodotto.getIdProdotto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Prodotto> doRetrieveByName(String nomeProdotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select idProdotto, nome, prezzo, descrizione, immagine, vetrina, sconto, quantità, nomecategoria from prodotto where nome LIKE ?");
            ps.setString(1, "%" + nomeProdotto + "%");
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();

            while (rs.next()) {

                Prodotto p = new Prodotto();
                p.setIdProdotto(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setPrezzo(rs.getInt(3));
                p.setDescrizione(rs.getString(4));
                p.setImmagine(rs.getString(5));
                p.setVetrina(rs.getBoolean(6));
                p.setSconto(rs.getInt(7));
                p.setQuantità(rs.getInt(8));
                p.setNomeCategoria(rs.getString(9));
                prodotti.add(p);
            }

            return prodotti;

        } catch (SQLException s) {

            throw new RuntimeException(s);
        }
    }
    // Elimina un prodotto dal database dato l'id del prodotto
    public void doDelete(int idProdotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM prodotto WHERE idProdotto = ?");
            ps.setInt(1, idProdotto);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
