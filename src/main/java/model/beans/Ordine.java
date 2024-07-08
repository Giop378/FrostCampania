package model.beans;

import java.util.Objects;

public class Ordine {
    private int idOrdine;
    private int prezzo;
    private int idUtente;
    private int cap;
    private int numeroCivico;
    private String nome;
    private String cognome;
    private String via;
    private String telefono;
    private String nomeMetodoPagamento ;
    private String nomeMetodoSpedizone;

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNomeMetodoPagamento() {
        return nomeMetodoPagamento;
    }

    public void setNomeMetodoPagamento(String nomeMetodoPagamento) {
        this.nomeMetodoPagamento = nomeMetodoPagamento;
    }

    public String getNomeMetodoSpedizone() {
        return nomeMetodoSpedizone;
    }

    public void setNomeMetodoSpedizone(String nomeMetodoSpedizone) {
        this.nomeMetodoSpedizone = nomeMetodoSpedizone;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !( o instanceof Ordine ) ) return false;
        Ordine ordine = (Ordine) o;
        return getIdOrdine() == ordine.getIdOrdine() && getPrezzo() == ordine.getPrezzo() && getIdUtente() == ordine.getIdUtente() && getCap() == ordine.getCap() && getNumeroCivico() == ordine.getNumeroCivico() && Objects.equals(getNome(), ordine.getNome()) && Objects.equals(getCognome(), ordine.getCognome()) && Objects.equals(getVia(), ordine.getVia()) && Objects.equals(getTelefono(), ordine.getTelefono()) && Objects.equals(getNomeMetodoPagamento(), ordine.getNomeMetodoPagamento()) && Objects.equals(getNomeMetodoSpedizone(), ordine.getNomeMetodoSpedizone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrdine(), getPrezzo(), getIdUtente(), getCap(), getNumeroCivico(), getNome(), getCognome(), getVia(), getTelefono(), getNomeMetodoPagamento(), getNomeMetodoSpedizone());
    }
}
