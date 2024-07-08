package model.DAO;

import model.beans.ConPool;
import model.beans.MetodoPagamento;
import model.beans.Ordine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    // Recupera una lista di tutti gli ordini dal database
    public List<Ordine> doRetrieveAll() {


        try (Connection con = ConPool.getConnection()) {



            PreparedStatement ps = con.prepareStatement("select IdOrdine,prezzo,Idutente,cap,numerocivico,nome,cognome,via,telefono,nomemetodopagamento,nomemetodospedizione from ordine");
            ResultSet rs = ps.executeQuery();

            List<Ordine>  ordini = new ArrayList<>();

            while (rs.next()) {

               Ordine c = new Ordine();

                c.setIdOrdine(rs.getInt(1));
                c.setPrezzo(rs.getInt(2));
                c.setIdUtente(rs.getInt(3));
                c.setCap(rs.getInt(4));
                c.setNumeroCivico(rs.getInt(5));
                c.setNome(rs.getString(6));
                c.setCognome(rs.getString(7));
                c.setVia(rs.getString((8)));
                c.setTelefono(rs.getString(9));
                c.setNomeMetodoPagamento(rs.getString(10));
                c.setNomeMetodoSpedizone(rs.getString(11));

                ordini.add(c);
            }

            return ordini;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    // Dato un id recupera un ordine
    public Ordine doRetrieveByIdOrder(int id) {
        try (Connection con = ConPool.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT IdOrdine, prezzo, IdUtente, cap, numerocivico, nome, cognome, via, telefono, nomemetodopagamento, nomemetodospedizione " +
                            "FROM ordine " +
                            "WHERE IdOrdine = ?");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();


            if(rs.next()) {
                Ordine o = new Ordine();
                o.setIdOrdine(rs.getInt(1));
                o.setPrezzo(rs.getInt(2));
                o.setIdUtente(rs.getInt(3));
                o.setCap(rs.getInt(4));
                o.setNumeroCivico(rs.getInt(5));
                o.setNome(rs.getString(6));
                o.setCognome(rs.getString(7));
                o.setVia(rs.getString(8));
                o.setTelefono(rs.getString(9));
                o.setNomeMetodoPagamento(rs.getString(10));
                o.setNomeMetodoSpedizone(rs.getString(11));

                return o;
            }

            return null;

        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }

    // Recupera una lista di ordini dal database filtrati per ID utente
    public List<Ordine> doRetrieveByCustomer(int idUtente) {
        try (Connection con = ConPool.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT IdOrdine, prezzo, IdUtente, cap, numerocivico, nome, cognome, via, telefono, nomemetodopagamento, nomemetodospedizione " +
                            "FROM ordine " +
                            "WHERE IdUtente = ?");

            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            List<Ordine> ordini = new ArrayList<>();

            while (rs.next()) {
                Ordine o = new Ordine();
                o.setIdOrdine(rs.getInt(1));
                o.setPrezzo(rs.getInt(2));
                o.setIdUtente(rs.getInt(3));
                o.setCap(rs.getInt(4));
                o.setNumeroCivico(rs.getInt(5));
                o.setNome(rs.getString(6));
                o.setCognome(rs.getString(7));
                o.setVia(rs.getString(8));
                o.setTelefono(rs.getString(9));
                o.setNomeMetodoPagamento(rs.getString(10));
                o.setNomeMetodoSpedizone(rs.getString(11));

                ordini.add(o);
            }

            return ordini;

        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
    }

    // Salva un nuovo ordine nel database e restituisce l'ID generato
    public int doSave(Ordine ordine) {
        try (Connection con = ConPool.getConnection()) {
            String sql = "INSERT INTO ordine (prezzo, IdUtente, cap, numerocivico, nome, cognome, via, telefono, nomemetodopagamento, nomemetodospedizione) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, ordine.getPrezzo());
            ps.setInt(2, ordine.getIdUtente());
            ps.setInt(3, ordine.getCap());
            ps.setInt(4, ordine.getNumeroCivico());
            ps.setString(5, ordine.getNome());
            ps.setString(6, ordine.getCognome());
            ps.setString(7, ordine.getVia());
            ps.setString(8, ordine.getTelefono());
            ps.setString(9, ordine.getNomeMetodoPagamento());
            ps.setString(10, ordine.getNomeMetodoSpedizone());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            // Recupera l'ID generato
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new RuntimeException("Failed to retrieve generated ID.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
