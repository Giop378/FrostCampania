package model.beans;

public class Carrello {
    private Integer idUtente;
    private int idProdotto;
    private int quantità;

    private int prezzoProdotto;
    private String nomeProdotto;
    private String immagineProdotto;

    public int getPrezzoProdotto() {
        return prezzoProdotto;
    }

    public void setPrezzoProdotto(int prezzoProdotto) {
        this.prezzoProdotto = prezzoProdotto;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public String getImmagineProdotto() {
        return immagineProdotto;
    }

    public void setImmagineProdotto(String immagineProdotto) {
        this.immagineProdotto = immagineProdotto;
    }
    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantità() {
        return quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }
}
