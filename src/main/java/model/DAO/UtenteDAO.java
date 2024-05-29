package model.DAO;

import model.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UtenteDAO {
    public Utente doRetrieveByEmailPassword(String email, String passwordhash) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        Utente utente = null;

        try (Connection connection = ConPool.getConnection()) {
            statement = connection.prepareStatement("SELECT idUtente, nome, cognome, email, passwordhash, datadinascita, adminCheck FROM utente WHERE email=? AND passwordhash=SHA1(?)");
            statement.setString(1, email);
            statement.setString(2, passwordhash);
            rs = statement.executeQuery();

            if (rs.next()) {
                utente = new Utente();
                utente.setIdUtente(rs.getInt("idutente"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEmail(rs.getString("email"));
                utente.setPasswordhash(passwordhash);   //fornisco la password non criptata in modo che poi verrà criptata dal metodo setPasswordhash
                utente.setDataDiNascita(rs.getDate("datadinascita").toLocalDate());
                utente.setAdminCheck(rs.getBoolean("adminCheck"));
            }

        }  catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs!= null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return utente;
    }

    public void doSave(Utente utente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO utente (nome, cognome, email, passwordhash, datadinascita, adminCheck) VALUES(?,?,?,?,?,?)"
            );
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPasswordhash());
            ps.setDate(5, java.sql.Date.valueOf(utente.getDataDiNascita()));
            ps.setBoolean(6, utente.isAdminCheck());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
