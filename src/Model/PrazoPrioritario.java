package Model;

public class PrazoPrioritario implements PrazoEntrega {
    @Override
    public int obterDiasUteis() {
        return 1;
    }

    @Override
    public String obterDescricao() {
        return "1 dia útil (Expresso)";
    }
}
