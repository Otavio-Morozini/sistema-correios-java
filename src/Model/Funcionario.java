package Model;

public class Funcionario extends  Pessoa{
    private TipoVeiculo veiculo;

    public Funcionario(String nome, String cpf, TipoVeiculo veiculo) {
        super(nome, cpf);
        this.veiculo = veiculo;
    }
    public TipoVeiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(TipoVeiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String toString() {
        return "Funcionário: " + getNome() + " | CPF: " + getCpf() + " | " + veiculo.toString();
    }
}
