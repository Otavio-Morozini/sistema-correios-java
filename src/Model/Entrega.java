package Model;

public class Entrega {
    private Funcionario funcionario;
    private Encomenda encomenda;
    private ModalidadeFrete modalidadeFrete;
    private StatusEntrega status;


    public Entrega(Funcionario funcionario, Encomenda encomenda, ModalidadeFrete modalidadeFrete) {
        this.funcionario = funcionario;
        this.encomenda = encomenda;
        this.modalidadeFrete = modalidadeFrete;
        this.status = StatusEntrega.PENDENTE;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }
    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public ModalidadeFrete getModalidadeFrete() {
        return modalidadeFrete;
    }
    public void setModalidadeFrete(ModalidadeFrete modalidadeFrete) {
        this.modalidadeFrete = modalidadeFrete;
    }

    public double calcularTotalFrete(){
        return modalidadeFrete.calcularValor(encomenda.getPeso());
    }

    public String toString() {
        return "[ENTREGA - Status: " + this.status + "]\n" +
                "Cliente: " + encomenda.getDestinatario().getNome() + " (CPF: " + encomenda.getDestinatario().getCpf() + ")\n" +
                "Endereço: " + encomenda.getDestinatario().getEndereco().toString()+ "\n" +
                "Encomenda: " + encomenda.getNome() + " | Peso: " + encomenda.getPeso() + "kg\n" +
                "Entregador: " + funcionario.getNome() + " | Veículo: " + funcionario.getVeiculo().toString() + "\n" +
                "Valor do Frete: R$ " + String.format("%.2f", calcularTotalFrete());
    }

}
