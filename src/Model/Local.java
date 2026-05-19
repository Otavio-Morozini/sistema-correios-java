package Model;

public class Local {

    private String nomeDaRua;
    private String numeroDaCasa;
    private String bairro;
    private String cep;

    public Local(String nomeDaRua, String numeroDaCasa, String bairro, String cep) {
        this.nomeDaRua = nomeDaRua;
        this.numeroDaCasa = numeroDaCasa;
        this.bairro = bairro;
        this.cep = cep;
    }

    public String getNomeDaRua() {
        return nomeDaRua;
    }
    public void setNomeDaRua(String nomeDaRua) {
        this.nomeDaRua = nomeDaRua;
    }

    public String getNumeroDaCasa() {
        return numeroDaCasa;
    }
    public void setNumeroDaCasa(String numeroDaCasa) {
        this.numeroDaCasa = numeroDaCasa;
    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String toString() {
        return nomeDaRua + ", " + numeroDaCasa + " - " + bairro + " (CEP: " + cep + ")";
    }


}
