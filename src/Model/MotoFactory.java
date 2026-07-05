package Model;

public class MotoFactory implements VeiculoFactory {
    @Override
    public TipoVeiculo criarVeiculo(String placa, String cor) {
        return new TipoVeiculo("Moto", 50.0, placa, cor);
    }
}
