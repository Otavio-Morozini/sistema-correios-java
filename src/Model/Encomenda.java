package Model;

public class Encomenda {
    private String nome;
    private String descricao;
    private double peso;
    private String tamanho;
    private Cliente destinatario;

    public Encomenda(String nome, String descricao, double peso, String tamanho, Cliente destinatario) {
        this.nome = nome;
        this.descricao = descricao;
        setPeso(peso);
        this.tamanho = tamanho;
        this.destinatario = destinatario;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("O peso da encomenda deve ser maior que zero!");
        }
        this.peso = peso;
    }

    public String getTamanho() {
        return tamanho;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public String toString() {
        return "Encomenda: " + nome + " (" + tamanho + ") - " + peso + "kg\nDescrição: " + descricao;
    }
}
