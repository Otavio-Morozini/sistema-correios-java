package Model;

public class CaminhaoFactory implements VeiculoFactory {
    @Override
    public TipoVeiculo criarVeiculo(String placa, String cor) {
        return new TipoVeiculo("Caminhão", 2000.0, placa, cor);
    }
}
