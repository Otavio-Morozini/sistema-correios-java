package Model;

public class CarroFactory implements VeiculoFactory {
    @Override
    public TipoVeiculo criarVeiculo(String placa, String cor) {
        return new TipoVeiculo("Carro", 150.0, placa, cor);
    }
}
