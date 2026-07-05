package Model;

public class LogisticaPadraoFactory implements LogisticaFactory {
    @Override
    public ModalidadeFrete criarFrete() {
        return new FretePadrao();
    }

    @Override
    public PrazoEntrega criarPrazo() {
        return new PrazoNormal();
    }
}
