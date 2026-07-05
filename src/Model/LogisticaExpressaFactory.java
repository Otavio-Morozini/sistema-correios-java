package Model;

public class LogisticaExpressaFactory implements LogisticaFactory {
    @Override
    public ModalidadeFrete criarFrete() {
        return new FreteExpresso();
    }

    @Override
    public PrazoEntrega criarPrazo() {
        return new PrazoPrioritario();
    }
}
