package model;

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
    private String nomeMetodoPagaamneto ;
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

    public String getNomeMetodoPagaamneto() {
        return nomeMetodoPagaamneto;
    }

    public void setNomeMetodoPagaamneto(String nomeMetodoPagaamneto) {
        this.nomeMetodoPagaamneto = nomeMetodoPagaamneto;
    }

    public String getNomeMetodoSpedizone() {
        return nomeMetodoSpedizone;
    }

    public void setNomeMetodoSpedizone(String nomeMetodoSpedizone) {
        this.nomeMetodoSpedizone = nomeMetodoSpedizone;
    }
}
