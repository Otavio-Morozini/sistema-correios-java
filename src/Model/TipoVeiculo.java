package Model;

public class TipoVeiculo {
    private String tipo; //Carro, Moto ou caminhão
    private double peso; //Capacidade de carga
    private String placa;
    private String cor;

    public TipoVeiculo(String tipo, double peso, String placa, String cor) {
        this.tipo = tipo;
        this.peso = peso;
        this.placa = placa;
        this.cor = cor;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("A capacidade de carga do veículo deve ser maior que zero!");
        }
        this.peso = peso;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    public String toString() {
        return tipo + " [Placa: " + placa + ", Cor: " + cor + ", Peso: " + peso + "kg]";
    }
}
