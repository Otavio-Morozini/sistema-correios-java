package Model;

public class Cliente extends Pessoa{
    private Local endereco;

    public Cliente(String nome, String cpf, Local endereco) {
        super(nome, cpf);
        this.endereco = endereco;
    }
    public Local getEndereco() {
        return endereco;
    }

    public void setEndereco(Local endereco) {
        this.endereco = endereco;
    }
    public String toString() {
        return "Cliente: " + getNome() + " | CPF: " + getCpf();
    }

}
